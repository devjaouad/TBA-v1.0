/*Developer: Jaouad Mouaou
 *Date: 12/09/2015
 *Application: TBA v1.0
 */
package Entities;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class DialogMessage {

    final JDialog dialog = new JDialog();

    public void Message(String Message) {        
        dialog.setAlwaysOnTop(true);
        JOptionPane.showMessageDialog(dialog, Message);

    }

}
