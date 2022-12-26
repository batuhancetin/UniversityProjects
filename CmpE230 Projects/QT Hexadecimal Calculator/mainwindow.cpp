#include "mainwindow.h"
#include "ui_mainwindow.h"
#include <cstdlib>
#include <iostream>
using namespace std;

int calculated = 0;                 // calculated variable keeps the cumulavitve sum in the calculator
bool pluscheck = false;             // pluscheck variable keeps the last operator is plus or not
bool minuscheck = false;            // minuscheck variable keeps the last operator is minus or not
bool equalcheck = false;            // equalcheck variable keeps the last pressed button is equal or not
bool numcheck = false;              // numcheck variable keeps the last pressed button is number or not
bool operatorcheck = false;         // operatorcheck variable keeps the last pressed button is operator or not
int counter = 0;                    // counter variable counts how many number button is pressed to create a number
int operatorcounter = 0;            // operatorcounter variable keeps how many operator button is pressed in series
QString firstoperator = "";         // firstoperator variable keeps the first pressed operator button
QString secondoperator = "";        // secondoperator variable keeps the second pressed operator button

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    ui->TextDisplay->setText(QString::number(calculated));
    connect(ui->ZERO,SIGNAL(released()),this,SLOT(NumButton())); // connect function for ZERO button
    connect(ui->ONE,SIGNAL(released()),this,SLOT(NumButton())); // connect function for ONE button
    connect(ui->TWO,SIGNAL(released()),this,SLOT(NumButton())); // connect function for TWO button
    connect(ui->THREE,SIGNAL(released()),this,SLOT(NumButton())); // connect function for THREE button
    connect(ui->FOUR,SIGNAL(released()),this,SLOT(NumButton())); // connect function for FOUR button
    connect(ui->FIVE,SIGNAL(released()),this,SLOT(NumButton())); // connect function for FIVE button
    connect(ui->SIX,SIGNAL(released()),this,SLOT(NumButton())); // connect function for SIX button
    connect(ui->SEVEN,SIGNAL(released()),this,SLOT(NumButton())); // connect function for SEVEN button
    connect(ui->EIGHT,SIGNAL(released()),this,SLOT(NumButton())); // connect function for EIGHT button
    connect(ui->NINE,SIGNAL(released()),this,SLOT(NumButton())); // connect function for NINE button
    connect(ui->A,SIGNAL(released()),this,SLOT(NumButton())); // connect function for A button
    connect(ui->B,SIGNAL(released()),this,SLOT(NumButton())); // connect function for B button
    connect(ui->C,SIGNAL(released()),this,SLOT(NumButton())); // connect function for C button
    connect(ui->D,SIGNAL(released()),this,SLOT(NumButton())); // connect function for D button
    connect(ui->E,SIGNAL(released()),this,SLOT(NumButton())); // connect function for E button
    connect(ui->F,SIGNAL(released()),this,SLOT(NumButton())); // connect function for F button
    connect(ui->PLUS,SIGNAL(released()),this,SLOT(OperatorButton())); // connect function for PLUS button
    connect(ui->MINUS,SIGNAL(released()),this,SLOT(OperatorButton())); // connect function for MINUS button
    connect(ui->EQUAL,SIGNAL(released()),this,SLOT(EqualButton())); // connect function for EQUAL button
    connect(ui->CLEAR,SIGNAL(released()),this,SLOT(ClearButton())); // connect function for CLEAR button



}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::NumButton() { // SLOT function for number buttons
    operatorcounter = 0;
    QPushButton *number = (QPushButton *)sender(); // keeps the signal of pressed button
    QString numbervalue = number->text(); // keeps the which number pressed
    QString textdisplayvalue = ui->TextDisplay->text(); // keeps the text that was written on text display
    if(numcheck == false || operatorcheck == true || equalcheck == true) {
        if(equalcheck == true) { // if equal button pressed, set calculated to zero to create new number
            calculated = 0;
        }
        ui->TextDisplay->setText(numbervalue); // displays to text display screen which number is pressed
        operatorcheck = false;
        equalcheck = false;
    }
    else {
        QString newvalue = textdisplayvalue + numbervalue; // if there is a number that is being created this adds to new pressed number to right of it
        ui->TextDisplay->setText(newvalue); // displays number to text display screen
    }
    numcheck = true;
}
void MainWindow::OperatorButton() { // SLOT function for operator buttons(+ -)
    counter ++;
    operatorcounter ++;
    operatorcheck = true;
    QPushButton *myoperator = (QPushButton *)sender(); // keeps the signal of pressed button
    QString operatorvalue = myoperator->text(); // keeps the which operator pressed
    if(operatorcounter == 1) { // if the first operator is pressed
        firstoperator = operatorvalue;
        QString textdisplayvalue = ui->TextDisplay->text(); // keeps the text that was written on text display
        bool ok; // bool variable for toInt function
        int hexdisplay = textdisplayvalue.toInt(&ok, 16); // converts hexadecimal number string to integer
        int newnumber = 0;
        if(counter == 1) {
            calculated = hexdisplay;
            newnumber = hexdisplay;
        }
        else {
            if(pluscheck) { // if last operator is plus do summation
                newnumber = calculated + hexdisplay;
            }
            else if(minuscheck) { // if last operator is minus do minus
                newnumber = calculated - hexdisplay;
            }
        }

        calculated = newnumber;
        QString result = QString::number(calculated, 16).toUpper(); // converts new integer value to hexadecimal upper string
        ui->TextDisplay->setText(result); // display the result to text display screen
        pluscheck = false;
        minuscheck = false;
        equalcheck = false;
        if(QString::compare(operatorvalue,"+") == 0) { // if new operator is plus, set pluscheck true
            pluscheck = true;
        }
        else if(QString::compare(operatorvalue,"-") == 0) { // if new operator is minus, set minuscheck true
            minuscheck = true;
        }
    }
    else if(operatorcounter == 2) { // if the second operator is pressed
        secondoperator = operatorvalue;
        if(QString::compare(firstoperator,"+") == 0 && QString::compare(secondoperator,"-") == 0) { // if first operator is plus and second operator is minus, do minus process
            pluscheck = false;
            minuscheck = true;
        }
        else if(QString::compare(firstoperator,"-") == 0 && QString::compare(secondoperator,"-") == 0) { // if first operator is minus and second operator is minus, do plus process
            pluscheck = true;
            minuscheck = false;
        }
        else if(QString::compare(firstoperator,"-") == 0 && QString::compare(secondoperator,"+") == 0) { // if first operator is minus and second operator is plus, do minus process
            pluscheck = false;
            minuscheck = true;
        }
        else if(QString::compare(firstoperator,"+") == 0 && QString::compare(secondoperator,"+") == 0) { // if first operator is plus and second operator is plus, do plus process
            pluscheck = true;
            minuscheck = false;
        }
    }

}
void MainWindow::EqualButton() { // SLOT function for equal button
    operatorcounter = 0;
    if(equalcheck == false) { // if last pressed button is not equal button
        int newnumber = 0;
        QString textdisplayvalue = ui->TextDisplay->text(); // keeps the text that was written on text display
        bool ok; // bool variable for toInt function
        int hexdisplay = textdisplayvalue.toInt(&ok, 16); // converts hexadecimal number string to integer value
        if(pluscheck) { // if last operator is plus, do summation
            newnumber = calculated + hexdisplay;
        }
        if(minuscheck) { // if last operator is minus, do minus
            newnumber = calculated - hexdisplay;
        }
        calculated = newnumber;
        QString result = QString::number(newnumber, 16).toUpper(); // converts integer value to hexadecimal number string
        ui->TextDisplay->setText(result); // displays the result to text display screen
        equalcheck = true;
    }
}
void MainWindow::ClearButton() { // SLOT function for clear button
    operatorcounter = 0; // set operatorcounter to zero
    calculated = 0; // set calculated to zero
    pluscheck = false; // set pluscheck to false
    minuscheck = false; // set minuscheck to false
    equalcheck = false; // set equalcheck to false
    numcheck = false; // set numcheck to false
    counter = 0; // set counter to zero
    ui->TextDisplay->setText("0"); // displays zero on text display screen
}