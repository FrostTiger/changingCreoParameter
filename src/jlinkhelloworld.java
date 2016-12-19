// jlinkhelloworld.java
// imports required

import com.ptc.cipjava.*;
import com.ptc.pfc.pfcCommand.*;
import com.ptc.pfc.pfcGlobal.*;
import com.ptc.pfc.pfcModel.*;
import com.ptc.pfc.pfcModelItem.ParamValue;
import com.ptc.pfc.pfcModelItem.Parameter;
import com.ptc.pfc.pfcSession.*;
import java.io.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class jlinkhelloworld {

    static jlinkhelloworld App = null;
    String programName = null;
    Session session = null;
    FileWriter log = null;
    String msgFile = "msg_jlinkhelloworld.txt";
    String newline = null;

    // constructor
    //
    public jlinkhelloworld () {
        programName = this.getClass().getName();
        try {
            log = new FileWriter(programName + ".log");
            newline = System.getProperty("line.separator");
        }
        catch (Exception e) {
            // couldn't create log file, ignore
        }
    }

    // Display message in Pro/Engineer
    //
    public void DisplayMessage ( String mesg ) throws Exception {
        stringseq seq = stringseq.create();
        seq.set(0, mesg);
        session.UIDisplayMessage(msgFile, "JLHW %s", seq);
        seq.clear();
        writeLog(mesg);
    }

    // Write text to log file
    //
    public void writeLog ( String mesg ) {
        try {
            if (log == null) { return; }
            log.write(mesg + newline);
            log.flush();
        }
        catch (Exception e) {
            // ignore
        }
    }

    // Close log file
    //
    public void closeLog () {
        try {
            if (log == null) { return; }
            log.close();
        }
        catch (Exception e) {
            // ignore
        }
    }

    // Called by Pro/Engineer when starting the application
    //
    public static void startApp () {
        try {
            App = new jlinkhelloworld();
            App.startupApplication();
        }
        catch (Exception e) {
            App.writeLog("Problem running startupApplication method" + e.toString());
           
        }
    }

    // Called by Pro/Engineer when stopping the application
    //
    public static void stopApp () {
        try {
            App.shutdownApplication();
        }
        catch (Exception e) {
            App.writeLog("Problem running shutdownApplication method" + e.toString());
            
        }
    }

    // Perform some steps when shutting down the application
    //
    public void shutdownApplication () throws Exception {
        
        writeLog("Application '" + programName + "' stopped");
        closeLog();
    }

    // Perform some steps when starting the application
    //
    public void startupApplication () throws Exception {

        try {
            writeLog("Application '" + programName + "' started.");
            session = pfcGlobal.GetProESession();
        }
        catch (jxthrowable x) {
            writeLog("ERROR: Problem getting session object.");
            return;
        }

        UICommand btn1_cmd;

        try {
            // Define a UI command
			String iconFile = session.GetMessageContents(
            msgFile, "JLHW Btn1 icon", null);
            btn1_cmd = session.UICreateCommand(
                      "JLHW Btn1 Cmd", new JLHW_Btn1_CmdListener()
                  );
			btn1_cmd.SetIcon(iconFile);	
			btn1_cmd.Designate(msgFile,"JLHW Btn1 Label",null,null);
        }
        catch (jxthrowable x) {
            writeLog("ERROR: Problem creating uicmd.");
            return;
        }
        DisplayMessage(programName + " application started.");

    }
    // Callback for the 'Tools' menu button
    //
    public void Btn1_callback ( ) throws Exception {

        String mesg;
        Model model = session.GetCurrentModel();

        if (model == null) {
            mesg = "Hello! Goekhan SCHAMAN";
        }
        else {
            mesg = "Hello! The model is: " + model.GetFileName();
        }
        DisplayMessage(mesg);
        MainFrame screen=new MainFrame();
        screen.setVisible(true);
    }

    // Inner class for UI Command Listener
    //
    public class JLHW_Btn1_CmdListener extends DefaultUICommandActionListener {

        // Handler for button push
        //
        @Override
        public void OnCommand () {
            try {
                Btn1_callback();
            }
            catch (Exception e) {
                writeLog("Exception thrown by Btn1_callback method: " + e.toString());
            }
        }

    }
    public void changeDiameter(String message){
        //JOptionPane.showMessageDialog(null, message, "Sonuç" , JOptionPane.INFORMATION_MESSAGE);
    }
    public int getDiameter(DiameterType diameterType) throws Exception {
        session = pfcGlobal.GetProESession();
        Model model = session.GetCurrentModel();
        switch (diameterType) {
            case OUTER:
            {
                Parameter param=model.GetParam("DISCAP");
                ParamValue paramVal=param.GetValue();
                return paramVal.GetIntValue();
            }
            case INNER:
            {
                Parameter param=model.GetParam("ICCAP");
                ParamValue paramVal=param.GetValue();
                //JOptionPane.showMessageDialog(null, paramVal.GetIntValue(), "Sonuç" , JOptionPane.INFORMATION_MESSAGE);
                return paramVal.GetIntValue();
            }
            default:
                return 0;
        }
    
}
}