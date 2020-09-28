#import the required packages
import sys, os
from PyQt5.QtWidgets import QApplication, QMainWindow,QWidget,QPushButton, QLabel, QPlainTextEdit, QStatusBar, QToolBar, QVBoxLayout, QAction, QFileDialog, QMessageBox

from PyQt5.QtCore import Qt, QSize
from PyQt5.QtGui import QFontDatabase, QIcon, QKeySequence
from PyQt5.QtPrintSupport import QPrintDialog

#this is the class we should work on.
class App(QMainWindow):
    def __init__(self):
        super().__init__()

        self.setWindowIcon(QIcon('images/icon.ico'))
        self.screenWidth, self.screenHeight = self.geometry().width(), self.geometry().height()
        self.resize(self.screenWidth,self.screenHeight)

        self.filterTypes = 'All files(*);; Text Document (*.txt);; Python(*.py);; Markdown (*.md) '

        self.path = None

        fixedFont = QFontDatabase.systemFont(QFontDatabase.FixedFont)
        fixedFont.setPointSize(12)

        mainLayout = QVBoxLayout()

        #editor
        self.editor = QPlainTextEdit()
        self.editor.setFont(fixedFont)
        mainLayout.addWidget(self.editor)

        #status bar
        self.statusBar = self.statusBar()


        #app container
        container = QWidget()
        container.setLayout(mainLayout)
        self.setCentralWidget(container)

        #--------------------------------
        #File Menu
        #--------------------------------
        fileMenu = self.menuBar().addMenu('&File')

        #-----------------------------------
        # File Toolbar
        #-----------------------------------
        fileToolbar = QToolBar('File')
        fileToolbar.setIconSize(QSize(60,60))
        self.addToolBar(Qt.BottomToolBarArea, fileToolbar)

        ''''open, save, save as'''
        openFileAction = QAction(QIcon('images/open.ico'), 'Open File...', self)
        openFileAction.setStatusTip('Open File')
        openFileAction.setShortcut(QKeySequence.Open)
        openFileAction.triggered.connect(self.fileOpen)

        saveFileAction = self.createAction(self, 'images/save.ico', 'Save File', 'Save file',
                                             self.fileSave)
        saveFileAction.setShortcut(QKeySequence.Save)

        saveFileAsAction = self.createAction(self, 'images/save_as.ico', 'Save As File', 'Save As file',
                                             self.fileSaveAs)
        saveFileAsAction.setShortcut(QKeySequence('Ctrl+Shift+S'))

        fileMenu.addActions([openFileAction, saveFileAction, saveFileAsAction])
        fileToolbar.addActions([openFileAction, saveFileAction, saveFileAsAction])

        #Print Action(Print Document)
        printAction = self.createAction(self, 'images/print.ico', 'Print File', 'Print File',self.printFile)
        printAction.setShortcut(QKeySequence.Print)

        fileMenu.addAction(printAction)
        fileToolbar.addAction(printAction)

        #--------------------------------
        #Edit Menu
        #--------------------------------
        editMenu = self.menuBar().addMenu('&Edit')


        #--------------------------------
        #Edit Toolbar
        #--------------------------------
        editToolbar = QToolBar()
        editToolbar.setIconSize(QSize(60,60))
        self.addToolBar(Qt.BottomToolBarArea, editToolbar)

        '''undo redo actions'''
        undoAction = self.createAction(self, 'images/undo.ico','Undo','Undo',
                                       self.editor.undo)
        undoAction.setShortcut(QKeySequence.Undo)

        redoAction = self.createAction(self, 'images/redo.ico', 'Redo', 'Redo',
                                       self.editor.redo)
        redoAction.setShortcut(QKeySequence.Redo)

        editMenu.addActions([undoAction,redoAction])
        editToolbar.addActions([undoAction, redoAction])

        # Clear action
        clearAction = self.createAction(self, 'images/clean.ico','Clear','Clear',
                                        self.clearContent)
        editMenu.addAction(clearAction)
        editToolbar.addAction(clearAction)

        editMenu.addSeparator()
        editToolbar.addSeparator()


        cutAction = self.createAction(self,'images/cut.ico','Cut','Cut',
                                      self.editor.cut)
        copyAction = self.createAction(self, 'images/copy.ico', 'Copy', 'Copy',
                                      self.editor.copy)
        pasteAction = self.createAction(self, 'images/paste.ico', 'Paste', 'Paste',
                                      self.editor.paste)
        selectAllAction = self.createAction(self, 'images/cut.ico', 'Select all', 'Select all',
                                      self.editor.selectAll)

        cutAction.setShortcut(QKeySequence.Cut)
        copyAction.setShortcut(QKeySequence.Copy)
        pasteAction.setShortcut(QKeySequence.Paste)
        selectAllAction.setShortcut(QKeySequence.SelectAll)

        editMenu.addActions([cutAction,copyAction,pasteAction,selectAllAction])
        editToolbar.addActions([cutAction, copyAction, pasteAction, selectAllAction])
        editToolbar.addSeparator()

        #Wrap text
        wrapTextAction = self.createAction(self,'images/cut.ico','Wrap text','Wrap text',
                                           self.toggleWrapText)
        wrapTextAction.setShortcut(QKeySequence('Ctrl+Shift+W'))
        editMenu.addAction(wrapTextAction)
        editToolbar.addAction(wrapTextAction)




        self.updateTitle()
    
    #wraps the text
    def toggleWrapText(self):
        self.editor.setLineWrapMode(not self.editor.lineWrapMode())
    
    # clears everything 
    def clearContent(self):
        self.editor.setPlainText('')
    
    #open file action
    def fileOpen(self):
        path, _ = QFileDialog.getOpenFileName(
            parent=self,
            caption='Open File',
            directory='',
            filter=self.filterTypes
        )
        if path:
            try:
                with open(path,'r') as f:
                    text = f.read()
                    f.close()
            except Exception as e:
                self.dialogMessage(str(e))
            else:
                self.path = path
                self.editor.setPlainText(text)
                self.updateTitle()
    # save action
    def fileSave(self):
        if self.path is None:
            self.fileSaveAs()
        else:
            try:
                text = self.editor.toPlainText()
                with open(self.path, 'w') as f:
                    f.write(text)
                    f.close()
            except Exception as e:
                self.dialogMessage(str(e))


    # save as action
    def fileSaveAs(self):
        path, _ = QFileDialog.getSaveFileName(
            self,
            'Save File',
            '',
            self.filterTypes
        )
        text = self.editor.toPlainText()
        if not path:
            return
        else:
            try:
                with open(path,'w') as f:
                    f.write(text)
                    f.close()
            except Exception as e:
                self.dialogMessage(str(e))
            else:
                self.path = path
                self.updateTitle()
    #prints the file 
    def printFile(self):
        printDialog = QPrintDialog()
        if printDialog.exec_():
            self.editor.print_(printDialog.printer())

    #updates the title of the window
    def updateTitle(self):
         self.setWindowTitle('{0}'.format(os.path.basename(self.path) if self.path else 'Untitled'))

    #function to display a dialog message 
    def dialogMessage(self, message):
        dlg = QMessageBox(self)
        dlg.setText(message)
        dlg.setIcon(QMessageBox.Critical)



    #function to create an action
    def createAction(self, parent, iconPath, actionName, setStatusTip, triggeredMethod):
        action = QAction(QIcon(iconPath), actionName, parent)
        action.setStatusTip(setStatusTip)
        action.triggered.connect(triggeredMethod)
        return action




#execution starts here
if __name__==__main__:

    app = QApplication(sys.argv)
    notepad = App()
    notepad.show()

    sys.exit(app.exec_())
