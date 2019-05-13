#include <iostream>
#include <fstream>
#include <Windows.h>
#include <string>

#define MESSIZE 20
#define QSIZE 5

std::fstream file;
CHAR lpMutexName[] = "FileReading";

void create_file(const char* );
char* pop_queue();
void print(std::ostream&);


LPWSTR toLPWSTR(const char *c)
{
	const size_t cSize = strlen(c) + 1;
	LPWSTR wc = new WCHAR[cSize];
	mbstowcs(wc, c, cSize);

	return wc;
}

int main(int argc, char *argv[])
{
	if (argc != 2)
	{
		std::cout << "Invalid parameters. Try [path_to_exe] [binari_file_name]\n";
		return 1;
	}
	create_file(argv[1]);

	std::string path = "D:\\2term\\OperationSystem\\Lab_3\\Debug\\Sender.exe ";

	int num_sender = 0;
	std::cout << "Enter number of senders: ";
	std::cin >> num_sender;

	path += argv[1];

	HANDLE hMutex;
	hMutex = CreateMutex(NULL, FALSE, (WCHAR*)lpMutexName);

	if (hMutex == NULL)
		return GetLastError();

	STARTUPINFO *si_senders = new STARTUPINFO[num_sender];
	PROCESS_INFORMATION *piCom_senders = new PROCESS_INFORMATION[num_sender];

	HANDLE *hInEvents = new HANDLE[num_sender];
	HANDLE hSemaphoreEmpty, hSemaphoreFull;
	
	hSemaphoreEmpty = CreateSemaphore(NULL, 5, 5, toLPWSTR("EmptySemaphore"));
	hSemaphoreFull = CreateSemaphore(NULL, 0, 5, toLPWSTR("FullSemaphore"));

	for (int i = 0; i < num_sender; i++)
	{
		ZeroMemory(&si_senders[i], sizeof(STARTUPINFO));
		si_senders[i].cb = sizeof(STARTUPINFO);
		
		hInEvents[i] = CreateEvent(NULL, TRUE, FALSE, toLPWSTR(std::to_string(i).c_str()));
		
		if (hInEvents[i] == NULL)
			return GetLastError();

		CreateProcess(NULL, toLPWSTR((path + " " + std::to_string(i)).c_str()), NULL, NULL, FALSE,
			CREATE_NEW_CONSOLE, NULL, NULL, &si_senders[i], &piCom_senders[i]);
	}

	WaitForMultipleObjects(num_sender, hInEvents, TRUE, INFINITE);

	std::cout << "Recieved all signals\n";
	char *popped_string;
	
	while (true)
	{
		WaitForSingleObject(hSemaphoreFull, INFINITE);
		WaitForSingleObject(hMutex, INFINITE);
		popped_string = pop_queue();
		ReleaseMutex(hMutex);
		ReleaseSemaphore(hSemaphoreEmpty, 1, NULL);

		if (popped_string != NULL)
			std::cout << "popped: " << popped_string << std::endl;
		Sleep(1000);
	}
	
	CloseHandle(hMutex);
	for (int i = 0; i < num_sender; i++)
	{
		CloseHandle(piCom_senders[i].hProcess);
		CloseHandle(piCom_senders[i].hThread);
		CloseHandle(hInEvents[i]);
	}
	file.close();
	return 0;
}

void create_file(const char *filename)
{
	int first_element = 0, last_element = 0;
	std::fstream file_to_create(filename, std::ios::out);
	file.open(filename, std::ios::out | std::ios::in);
	file.clear();
	file.write(reinterpret_cast<char *>(&first_element), sizeof(int));
	file.write(reinterpret_cast<char *>(&last_element), sizeof(int));
	file.flush();
}

char* pop_queue()
{
	int first_element;

	file.seekg(0, std::ios::beg);
	file.read(reinterpret_cast<char *>(&first_element), sizeof(int));

	char *message = new char[MESSIZE];

	file.seekg(2 * sizeof(int) + (MESSIZE) * first_element, std::ios::beg);
	file.read(message, MESSIZE);

	first_element++;
	if (first_element > QSIZE - 1)
		first_element = 0;


	file.seekp(0, std::ios::beg);
	file.write(reinterpret_cast<char *>(&first_element), sizeof(int));

	file.flush();
	return message;
}