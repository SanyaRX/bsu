#include <fstream>
#include <iostream>
#include <Windows.h>
#include <chrono>
#include <random>
#include <string>
std::fstream file;

#define MESSIZE 20
#define QSIZE 5

CHAR lpMutexName[] = "FileReading";

void push_queue(const char*);
char* generate_string(int, char*);

LPWSTR toLPWSTR(const char *c)
{
	const size_t cSize = strlen(c) + 1;
	LPWSTR wc = new WCHAR[cSize];
	mbstowcs(wc, c, cSize);

	return wc;
}

int main(int argc, char *argv[])
{
	srand(time(NULL));

	if (argc != 3)
	{
		std::cout << "Invalid parameters. Try [path_to_exe] [binari_file_name] [event_name]\n";
		return 1;
	}
	
	file.open(argv[1], std::ios::in | std::ios::out);

	HANDLE hMutex;
	hMutex = OpenMutex(SYNCHRONIZE, FALSE, (WCHAR*)lpMutexName);

	if (hMutex == NULL)
		std::cout << GetLastError() << "\n";

	HANDLE hInEvent;
	hInEvent = OpenEvent(EVENT_MODIFY_STATE, FALSE, toLPWSTR(argv[2]));
	
	HANDLE hSemaphoreEmpty, hSemaphoreFull;
	hSemaphoreEmpty = OpenSemaphore(SYNCHRONIZE | EVENT_MODIFY_STATE, FALSE, toLPWSTR("EmptySemaphore"));
	hSemaphoreFull = OpenSemaphore(SYNCHRONIZE | EVENT_MODIFY_STATE, FALSE, toLPWSTR("FullSemaphore"));

	if (hInEvent == NULL)
		std::cout << GetLastError() << "\n";
	
	SetEvent(hInEvent);
	while (true)
	{
		char* message = generate_string(5, argv[2]);
		WaitForSingleObject(hSemaphoreEmpty, INFINITE);
		WaitForSingleObject(hMutex, INFINITE);
		push_queue(message);
		ReleaseMutex(hMutex);
		ReleaseSemaphore(hSemaphoreFull, 1, NULL);
		std::cout << "pushed: " << message << std::endl;
		delete[] message;
		Sleep(500);
	}
	
	CloseHandle(hMutex);
	CloseHandle(hInEvent);
	file.close();
	system("pause");
	return 0;
}

void push_queue(const char* message)
{
	if (std::strlen(message) > MESSIZE)
		return;

	file.seekg(0, std::ios::beg);
	int first_element, last_element;

	file.read(reinterpret_cast<char *>(&first_element), sizeof(int));
	file.read(reinterpret_cast<char *>(&last_element), sizeof(int));
	
	file.seekp(2 * sizeof(int) + (MESSIZE) * last_element, std::ios::beg);

	file.write(message, (MESSIZE));

	last_element++;
	if (last_element > QSIZE - 1)
		last_element = 0;

	file.seekp(sizeof(int), std::ios::beg);
	file.write(reinterpret_cast<char *>(&last_element), sizeof(int));
	file.flush();
}

char* generate_string(int length, char* number)
{
	std::mt19937 rng(std::chrono::steady_clock::now().time_since_epoch().count());
	std::uniform_int_distribution<> uid(0, 9);
	char *message = new char[20]{};
	strcat(message, "Sender #");
	strcat(message, number);
	strcat(message, ": ");

	for (int i = 11; i < 11 + length; i++)
		message[i] = uid(rng) + '0';
	//std::cout << "Generated: " << message << '\n';
	return message;
}