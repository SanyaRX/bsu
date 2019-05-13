#include "SyncQueue.h"

SyncQueue::SyncQueue(int nSize)
{
	size = nSize;
	queue = new int[size];
	head = 0;
	tail = 0;
	InitializeCriticalSection(&critical);
	empty = CreateSemaphore(NULL, nSize, nSize, NULL);
	full = CreateSemaphore(NULL, 0, nSize, NULL);
}
void SyncQueue::insert(int element)
{
	WaitForSingleObject(empty, INFINITE);	
	EnterCriticalSection(&critical);
	queue[tail] = element;
	tail++;
	if (tail >= size)
		tail = 0;
	LeaveCriticalSection(&critical);
	ReleaseSemaphore(full, 1, NULL);
}

int SyncQueue::remove()
{
	WaitForSingleObject(full, INFINITE);
	EnterCriticalSection(&critical);
	int top_element = queue[head];
	head++;
	if (head >= size)
		head = 0;
	LeaveCriticalSection(&critical);
	ReleaseSemaphore(empty, 1, NULL);
	return top_element;
}

SyncQueue::~SyncQueue()
{
	DeleteCriticalSection(&critical);
	CloseHandle(empty);
	CloseHandle(full);
	delete[] queue;
}
