#include <stdio.h>
#include <string>
#include "SyncQueue.h"

#define QSIZE 3

HANDLE *hReadyForWork;
HANDLE hStartWork;

SyncQueue queue(QSIZE);
CRITICAL_SECTION cs;

int message_number = 0;

DWORD WINAPI producer(LPVOID lpParameters)
{
	int product, num_product;
	int id = (int)lpParameters;

	EnterCriticalSection(&cs);
	printf("Producer #%d.\nEnter number to produce and amount: ", id);
	scanf("%d %d", &product, &num_product);
	LeaveCriticalSection(&cs);

	SetEvent(hReadyForWork[id]);
	WaitForSingleObject(hStartWork, INFINITE);
	
	for (int i = 0; i < num_product; i++)
	{
		queue.insert(product);
		EnterCriticalSection(&cs);
		printf("\tProducer #%d inserterd %d. Message #%d\n", id, product, message_number++);
		LeaveCriticalSection(&cs);
		Sleep(500);
	}
	return 0;
}

DWORD WINAPI consumer(LPVOID lpParameters)
{
	int id = (int)lpParameters;
	int num_to_consume;
	EnterCriticalSection(&cs);
	printf("\nConsumer #%d.\nEnter amount of products to consume: ", id);
	scanf("%d", &num_to_consume);
	LeaveCriticalSection(&cs);

	SetEvent(hReadyForWork[id]);
	WaitForSingleObject(hStartWork, INFINITE);

	for (int i = 0; i < num_to_consume; i++)
	{
		int number = queue.remove();
		EnterCriticalSection(&cs);
		printf("Consumer #%d consumed %d.\n", id, number);
		LeaveCriticalSection(&cs);
		Sleep(1000);
	}
	return 0;
}

LPWSTR toLPWSTR(const char *c)
{
	const size_t cSize = strlen(c) + 1;
	LPWSTR wc = new WCHAR[cSize];
	mbstowcs(wc, c, cSize);

	return wc;
}

int main(int argc, char* argv[])
{
	if (argc != 3)
	{
		printf("Invalid parametrs. Use [producer_number] [consumer_number].\n");
		return 0;
	}
	
	int producer_num = atoi(argv[1]);
	int consumer_num = atoi(argv[2]);

	hReadyForWork = new HANDLE[producer_num + consumer_num];
	
	hStartWork = CreateEvent(NULL, TRUE, FALSE, NULL);
	HANDLE *hProducers = new HANDLE[producer_num];
	HANDLE *hConsumers = new HANDLE[consumer_num];
	InitializeCriticalSection(&cs);
	for (int i = 0; i < producer_num; i++)
	{
		hProducers[i] = CreateThread(NULL, 0, producer, (LPVOID)i, 0, NULL);
		if (hProducers[i] == NULL)
			return GetLastError();
		
		hReadyForWork[i] = CreateEvent(NULL, TRUE, FALSE, NULL);
		if (hReadyForWork == NULL)
			return GetLastError();
	}

	for (int i = producer_num; i < producer_num + consumer_num; i++)
	{
		hConsumers[i - producer_num] = CreateThread(NULL, 0, consumer, (LPVOID)i, 0, NULL);
		if (hConsumers[i - producer_num] == NULL)
			return GetLastError();
		
		hReadyForWork[i] = CreateEvent(NULL, TRUE, FALSE, NULL);
		if (hReadyForWork == NULL)
			return GetLastError();
	}
	
	WaitForMultipleObjects(producer_num + consumer_num, hReadyForWork, TRUE, INFINITE);
	printf("\nProducers and consumers are ready for work.\n");
	
	SetEvent(hStartWork);

	WaitForMultipleObjects(consumer_num, hConsumers, TRUE, INFINITE);
	WaitForMultipleObjects(producer_num, hProducers, TRUE, INFINITE);
	
	for (int i = 0; i < producer_num; i++)
	{
		CloseHandle(hProducers[i]);
		CloseHandle(hReadyForWork[i]);
	}
	
	for (int i = 0; i < consumer_num; i++)
	{
		CloseHandle(hConsumers[i]);
		CloseHandle(hReadyForWork[consumer_num + i]);
	}

	CloseHandle(hStartWork);

	DeleteCriticalSection(&cs);
	return 0;
}