# Editor
Advanced software development Homework Assignment # 6
One of the rules of the Refractory is writing nouns with an intelligible meaning and indicating the instrument of its use
I returned the names of the variables and method,
I also preferred to put variables and methods  to be private,
I deleted the code that was written and was in a comment because it is one of the rules that it is not permissible to keep a code that I do not use in the code

Then I followed one style
in writing the names of variables and method
It is writing the first letter of the letter Smol from the first word and the word that follows it begins with the letter capital letter,
{
 private JEditorPane PT -> private JEditorPane textPanel;
 ActionEvent e ->  public void actionPerformed(ActionEvent event)
 int ans -> int answer = 0;
 .....

}

Then I arranged the method and put the tasks that exist in more than one method in a new method in order to avoid repetition,
arrange the code and also, as in the rules, reduce the size of the method


{

    private void prepareKey(JMenuItem item, char mnemonic, int key){
        item.setMnemonic(mnemonic);
        item.setAccelerator(KeyStroke.getKeyStroke(key, InputEvent.CTRL_DOWN_MASK));
        item.addActionListener(this);

    }
    I used this method to reduce the redundant code in the { buildFileMenu , buildEditMenu }
    


}


Then it was used to reduce the method size of refactoring rules. If the size of the method is large,
then it should be divided so as not to get lost in it and not be properly understood.

{
 public void actionPerformed(ActionEvent event) {
        String action = event.getActionCommand();
        if (action.equals("Quit")) {
            System.exit(0);
        } else if (action.equals("Open")) {
            loadFile();
        } else if (action.equals("Save")) {
            saveFile();
        } else if (action.equals("New")) {
            newFile();
        }
           private void saveFile() {
        //Save file
        int answer = 0;
        if (changed) {
            // 0 means yes and no option, 2 Used for warning messages.
            answer = JOptionPane.showConfirmDialog(null, "The file has changed. You want to save it?", "Save file", 0, 2);
        }
        //1 value from class method if NO is chosen.
        if (answer != 1) {
            if (file == null) {
                saveAs("Save");
            } else {
                printWriter();
            }
        }
    }
        private void newFile() {
            if (changed) {
                //Save file
                if (changed) {
                    // 0 means yes and no option, 2 Used for warning messages.
                    int ans = JOptionPane.showConfirmDialog(null, "The file has changed. You want to save it?", "Save file",
                            0, 2);
                    //1 value from class method if NO is chosen.
                    if (ans == 1)
                        return;
                }
                if (file == null) {
                    saveAs("Save");
                    return;
                }

                printWriter();
            }
            file = null;
        textPanel.setText("");
            changed = false;
            setTitle("Editor");
        }
}

Then also to reduce the size of code  the new module I created a new module and put the try and also catch,
the same in the code to reduce the size of the code and not to repeat
{
    private void saveFile() {
        //Save file
        int answer = 0;
        if (changed) {
            // 0 means yes and no option, 2 Used for warning messages.
            answer = JOptionPane.showConfirmDialog(null, "The file has changed. You want to save it?", "Save file", 0, 2);
        }
        //1 value from class method if NO is chosen.
        if (answer != 1) {
            if (file == null) {
                saveAs("Save");
            } else {
                printWriter();
            }
        }
    }
        private void newFile() {
            if (changed) {
                //Save file
                if (changed) {
                    // 0 means yes and no option, 2 Used for warning messages.
                    int ans = JOptionPane.showConfirmDialog(null, "The file has changed. You want to save it?", "Save file",
                            0, 2);
                    //1 value from class method if NO is chosen.
                    if (ans == 1)
                        return;
                }
                if (file == null) {
                    saveAs("Save");
                    return;
                }
                     printWriter();
            }
            file = null;
        textPanel.setText("");
            changed = false;
            setTitle("Editor");
        }
         private void printWriter(){
        String text = textPanel.getText();
        System.out.println(text);
        try (PrintWriter writer = new PrintWriter(file);){
            if (!file.canWrite())
                throw new Exception("Cannot write file!");
            writer.write(text);
            changed = false;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}





