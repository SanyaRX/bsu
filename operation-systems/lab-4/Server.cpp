#include <iostream>
#include <windows.h>
#include <conio.h>

#define CNSIZE 10

typedef struct _MESSAGE {
	char sender[10]; // sender name
	char receiver[10]; // receiver name
	char text[20]; // message text
} message;

LPWSTR toLPWSTR(const char *c)
{
	const size_t cSize = strlen(c) + 1;
	LPWSTR wc = new WCHAR[cSize];
	mbstowcs(wc, c, cSize);

	return wc;
}

int main(int argc, char *argv[])
{
	STARTUPINFO *si;
	PROCESS_INFORMATION *pi;
	HANDLE *hClientWritePipe, *hClientReadPipe;
	HANDLE *hReadyToRead;
	SECURITY_ATTRIBUTES sa;
	char **client_names;

	char lpszComLine[80];

	int num_clients;
	std::cout << "Enter number of clients: ";
	std::cin >> num_clients;
	
	client_names = new char*[num_clients];

	si = new STARTUPINFO[num_clients];
	pi = new PROCESS_INFORMATION[num_clients];
	hClientWritePipe = new HANDLE[num_clients];
	hClientReadPipe = new HANDLE[num_clients];
	hReadyToRead = new HANDLE[num_clients];

	sa.nLength = sizeof(SECURITY_ATTRIBUTES);
	sa.lpSecurityDescriptor = NULL;
	sa.bInheritHandle = TRUE;

	for (int i = 0; i < num_clients; i++)
	{
		client_names[i] = new char[CNSIZE];
		std::cout << "\nEnter client #" << (i + 1) << " name: ";
		std::cin >> client_names[i];

		ZeroMemory(&si[i], sizeof(STARTUPINFO));
		si[i].cb = sizeof(STARTUPINFO);
	
		if (!CreatePipe(&hClientReadPipe[i], &hClientWritePipe[i], &sa, NULL))
		{
			std::cout << "\nCannot create pipe\n"
				<< "Press any button to continue...";
			_getch();
			return GetLastError();
		}
		
		hReadyToRead[i] = CreateEvent(&sa, FALSE, FALSE, NULL);
		sprintf(lpszComLine, "D:\\2term\\OperationSystem\\Lab-4\\Debug\\Client.exe %s %d %d %d", // here you need 
																								   // to enter the path to Client.exe file
			client_names[i], (int)hClientWritePipe[i], (int)hClientReadPipe[i], (int)hReadyToRead[i]);

		if (!CreateProcess(NULL, toLPWSTR(lpszComLine), NULL, NULL, TRUE, CREATE_NEW_CONSOLE, NULL, NULL, &si[i], &pi[i]))
		{
			std::cout << "\nCannot create process\n"
				<< "Press any button to continue...";
			_getch();
			return GetLastError();
		}

		CloseHandle(pi[i].hProcess);
		CloseHandle(pi[i].hThread);
		
	}

	
	/* Loop for handling messages */
	while (true)
	{
		DWORD dwBytesRead;
		message message_to_read;
		
		int index = WaitForMultipleObjects(num_clients, hReadyToRead, FALSE, INFINITE);
		
		if (ReadFile(hClientReadPipe[index], &message_to_read, sizeof(message_to_read), &dwBytesRead, NULL))
			std::cout << "Recieved message\n";
		for (int i = 0; i < num_clients; i++)
		{
			if (strcmp(client_names[i], message_to_read.receiver) == 0)
			{
				DWORD dwBytesWritten;
				WriteFile(hClientWritePipe[i], &message_to_read, sizeof(message_to_read), &dwBytesWritten, NULL);
				std::cout << "Sended message\n";
				break;
			}
		}

	}

	for (int i = 0; i < num_clients; i++)
	{
		CloseHandle(hClientWritePipe[i]);
		CloseHandle(hClientReadPipe[i]);
		CloseHandle(hReadyToRead[i]);
	}
		
	delete[] si;
	delete[] pi;
	delete[] hClientWritePipe;
	delete[] hClientReadPipe;
	delete[] hReadyToRead;
	return 0;
}