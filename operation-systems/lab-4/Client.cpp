#include <iostream>
#include <conio.h>
#include <windows.h>

#include <time.h>

typedef struct _MESSAGE {
	char sender[10]; // sender name
	char receiver[10]; // receiver name
	char text[20]; // message text
} message;

char name[10]{};

void complite_message(message &mes)
{
	strcpy(&mes.sender[0], name);

	std::cout << "\nEnter reciever name: ";
	std::cin >> mes.receiver;

	std::cout << "\nEnter message: ";
	std::cin >> mes.text;

}

int main(int argc, char *argv[])
{
	HANDLE hWritePipe, hReadPipe, hReadEvent;
	srand(time(NULL));

	strcat(name, argv[1]);
	hWritePipe = (HANDLE)atoi(argv[2]);
	hReadPipe = (HANDLE)atoi(argv[3]);
	
	hReadEvent = (HANDLE)atoi(argv[4]);
	/* Loop for creating, sending and receiving messages */
	bool work = true;
	while (work)
	{
		int choice;
		std::cout << std::endl << name << " menu\n1 - Send message\n2 - Receive messages\n3 - Quit\nYour choice: ";
		std::cin >> choice;
		switch(choice)
		{
			case 1: 
			{
				DWORD dwBytesWritten;
				message message_to_send;
				complite_message(message_to_send);

				if (!WriteFile(hWritePipe, &message_to_send, sizeof(message_to_send), &dwBytesWritten, NULL))
				{
					std::cout << "\nFailed message writing."
						<< "\nPress any button to continue...";
					_getch();
				}
				else
					SetEvent(hReadEvent);
				
				break;
			}
			case 2:
			{
				DWORD dwBytesRead;
				message message_to_read;
				if (ReadFile(hReadPipe, &message_to_read, sizeof(message_to_read), &dwBytesRead, NULL))
				{
					std::cout << "\nSender: " << message_to_read.sender
						<< "\n" << message_to_read.text << std::endl;
				}
				break;
			}
			case 3: work = false; break;
			default: std::cout << "\nInvalid choice.Try again...\n"; break;
		}
	}


	CloseHandle(hWritePipe);
	CloseHandle(hReadPipe);
	return 0;
}