package edu.najah.cap;



import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@SuppressWarnings("serial")
class Editor extends JFrame implements ActionListener, DocumentListener {

    public static  void main(String[] args) {new Editor();}

    private JEditorPane textPanel;
    private JMenuBar menu;
    private JMenuItem copy, paste, cut;
    private boolean changed = false;
    private File file;

    private Editor() {
        //Editor the name of our application
        super("Editor");
        textPanel = new JEditorPane();
        // center means middle of container.
        add(new JScrollPane(textPanel), "Center");
        textPanel.getDocument().addDocumentListener(this);

        menu = new JMenuBar();
        setJMenuBar(menu);
        buildMenu();
        //The size of window
        setSize(500, 500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void buildMenu() {
        buildFileMenu();
        buildEditMenu();
    }

    private void prepareKey(JMenuItem item, char mnemonic, int key){
        item.setMnemonic(mnemonic);
        item.setAccelerator(KeyStroke.getKeyStroke(key, InputEvent.CTRL_DOWN_MASK));
        item.addActionListener(this);

    }
    private void buildFileMenu() {
        JMenu file = new JMenu("File");
        file.setMnemonic('F');
        menu.add(file);
        JMenuItem newFile = new JMenuItem("New");
        prepareKey(newFile,'N', KeyEvent.VK_N);
        file.add(newFile);
        JMenuItem open = new JMenuItem("Open");
        prepareKey(open,'O', KeyEvent.VK_O);
        file.add(open);
        JMenuItem save = new JMenuItem("Save");
        prepareKey(save,'S', KeyEvent.VK_S);
        file.add(save);
        JMenuItem saveAs = new JMenuItem("Save as...");
        saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
        file.add(saveAs);
        saveAs.addActionListener(this);
        JMenuItem quit = new JMenuItem("Quit");
        file.add(quit);
        prepareKey(quit,'Q', KeyEvent.VK_Q);

    }
    private void buildEditMenu() {
        JMenu edit = new JMenu("Edit");
        menu.add(edit);
        edit.setMnemonic('E');
        // cut
        cut = new JMenuItem("Cut");
        prepareKey(cut,'T', KeyEvent.VK_X);
        edit.add(cut);
        // copy
        copy = new JMenuItem("Copy");
        prepareKey(copy,'C', KeyEvent.VK_C);
        edit.add(copy);
        // paste
        paste = new JMenuItem("Paste");
        prepareKey(paste,'P', KeyEvent.VK_V);
        edit.add(paste);
        JMenuItem find = new JMenuItem("Find");
        prepareKey(find,'F', KeyEvent.VK_F);
        edit.add(find);
        JMenuItem sallectAll = new JMenuItem("Select All");
        prepareKey(sallectAll,'A', KeyEvent.VK_A);
        edit.add(sallectAll);
    }

    @Override
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

       else if (action.equals("Save as...")) {
            saveAs("Save as...");
        } else if (action.equals("Select All")) {
            textPanel.selectAll();
        } else if (action.equals("Copy")) {
            textPanel.copy();
        } else if (action.equals("Cut")) {
            textPanel.cut();
        } else if (action.equals("Paste")) {
            textPanel.paste();
        } else if (action.equals("Find")) {
  //          FindDialog find = new FindDialog(this, true);
   //         find.showDialog();
        }
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

    private void loadFile() {
        JFileChooser dialog = new JFileChooser(System.getProperty("user.home"));
        dialog.setMultiSelectionEnabled(false);
        try {
            int result = dialog.showOpenDialog(this);

            if (result == 1)//1 value if cancel is chosen.
                return;
            if (result == 0) {// value if approve (yes, ok) is chosen.
                resultZero();

            }
        } catch (Exception exception) {
            exception.printStackTrace();
            //0 means show Error Dialog
            JOptionPane.showMessageDialog(null, exception, "Error", 0);
        }
    }

    private void resultZero() {
        if (changed){
            //Save file
            if (changed) {
                int ans = JOptionPane.showConfirmDialog(null, "The file has changed. You want to save it?", "Save file",
                        0, 2);//0 means yes and no question and 2 mean warning dialog
                if (ans == 1)// no option
                    return;
            }
            if (file == null) {
                saveAs("Save");
                return;
            }
            printWriter();

        }
        JFileChooser dialog = new JFileChooser(System.getProperty("user.home"));
      //  dialog.setMultiSelectionEnabled(false);
        file = dialog.getSelectedFile();
        //Read file
        StringBuilder readString = new StringBuilder();
        try (	FileReader filerRead = new FileReader(file);
                 BufferedReader reader = new BufferedReader(filerRead);) {
            String line;
            while ((line = reader.readLine()) != null) {
                readString.append(line + "\n");
            }
        } catch (IOException exception) {
            exception.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot read file !", "Error !", 0);//0 means show Error Dialog
        }

        textPanel.setText(readString.toString());
        changed = false;
        setTitle("Editor - " + file.getName());

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

    private void saveAs(String dialogTitle) {
        JFileChooser dialog = new JFileChooser(System.getProperty("user.home"));
        dialog.setDialogTitle(dialogTitle);
        int result = dialog.showSaveDialog(this);
        if (result != 0)//0 value if approve (yes, ok) is chosen.
            return;
        file = dialog.getSelectedFile();
        try (PrintWriter writer = new PrintWriter(file);){
            writer.write(textPanel.getText());
            changed = false;
            setTitle("Editor - " + file.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveAsText(String dialogTitle) {
        JFileChooser dialog = new JFileChooser(System.getProperty("user.home"));
        dialog.setDialogTitle(dialogTitle);
        int result = dialog.showSaveDialog(this);
        if (result != 0)//0 value if approve (yes, ok) is chosen.
            return;
        file = dialog.getSelectedFile();
        try (PrintWriter writer = new PrintWriter(file);){
            writer.write(textPanel.getText());
            changed = false;
            setTitle("Save as Text Editor - " + file.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void insertUpdate(DocumentEvent event) {
        changed = true;
    }

    @Override
    public void removeUpdate(DocumentEvent event) {
        changed = true;
    }

    @Override
    public void changedUpdate(DocumentEvent event) {
        changed = true;
    }

}
