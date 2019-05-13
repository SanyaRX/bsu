#include <iostream>
#include <fstream>
#include <string>
#include <windows.h>

struct tax_payment
{
	int num;
	char name[10];
	double sum;
};

LPWSTR toLPWSTR(const char *c)
{
	const size_t cSize = strlen(c) + 1;
	LPWSTR wc = new WCHAR[cSize];
	mbstowcs(wc, c, cSize);

	return wc;
}

int main(int argc, char argv[])
{
	std::string creator_path = "D:\\4term\\OperationSystem\\Lab1\\Main\\Debug\\Creator.exe";
	std::string reporter_path = "D:\\4term\\OperationSystem\\Lab1\\Main\\Debug\\Reporter.exe";
		
	std::string bynary_file_name;
	
	int payment_number_creator;
	
	std::cout << "Enter bynary file name: ";
	std::cin >> bynary_file_name;

	std::cout << "\nEnter payment number: ";
	std::cin >> payment_number_creator;

	creator_path += (" " + bynary_file_name + " " + std::to_string(payment_number_creator));

	STARTUPINFO si_creator, si_reporter;
	PROCESS_INFORMATION piCom_creator, piCom_reporter;

	ZeroMemory(&si_creator, sizeof(STARTUPINFO));
	ZeroMemory(&si_reporter, sizeof(STARTUPINFO));
	
	si_creator.cb = sizeof(STARTUPINFO);
	si_reporter.cb = sizeof(STARTUPINFO);
	
	CreateProcess(NULL, toLPWSTR(creator_path.c_str()), NULL, NULL, FALSE,
		CREATE_NEW_CONSOLE, NULL, NULL, &si_creator, &piCom_creator);

	WaitForSingleObject(piCom_creator.hProcess, INFINITE);
	
	CloseHandle(piCom_creator.hThread);
	CloseHandle(piCom_creator.hProcess);

	std::ifstream fin(bynary_file_name, std::ios::in);
	
	while (fin.peek() != EOF)
	{
		tax_payment temp;
		fin.read(reinterpret_cast<char*>(&temp), sizeof(tax_payment));
		std::cout << "Identify number: " << temp.num << "; Company name: " << temp.name
			<< "; Sum of tax payments: " << temp.sum << std::endl;
	}

	std::string report_file_name;
	double payment_sum;
	char symbol;

	std::cout << "\nEnter report file name: ";
	std::cin >> report_file_name;

	std::cout << "\nEnter payment sum: ";
	std::cin >> payment_sum;

	std::cout << "\nEnter symbol: ";
	std::cin >> symbol;

	reporter_path += (" " + bynary_file_name + " " + report_file_name + " "
		+ std::to_string(payment_sum) + " \"" + symbol + "\"");

	CreateProcess(NULL, toLPWSTR(reporter_path.c_str()), NULL, NULL, FALSE,
		CREATE_NEW_CONSOLE, NULL, NULL, &si_reporter, &piCom_reporter);

	WaitForSingleObject(piCom_reporter.hProcess, INFINITE);

	CloseHandle(piCom_reporter.hThread);
	CloseHandle(piCom_reporter.hProcess);

	std::ifstream ftin(report_file_name, std::ios::in);
	
	while (ftin.peek() != EOF)
	{
		std::string temp;
		std::getline(ftin, temp);
		std::cout << temp << std::endl;
	}

	fin.close();
	ftin.close();
	return 0;
}
