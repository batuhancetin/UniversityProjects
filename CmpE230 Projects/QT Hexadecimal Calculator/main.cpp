#include "mainwindow.h"

#include <QApplication>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv); // my application
    MainWindow w; // object of MainWindow class
    w.show(); // displays w object
    return a.exec();
}
