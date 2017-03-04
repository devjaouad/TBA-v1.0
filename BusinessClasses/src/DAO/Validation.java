/*Developer: Jaouad Mouaou
 *Date: 12/09/2015
 *Application: TBA v1.0
 */
package DAO;

import java.awt.event.KeyEvent;
import java.awt.Toolkit;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class Validation extends PlainDocument {

    private static final long serialVersionUID = 1L;

    private final int limit;

    public Validation(int limitation) {
        super();
        this.limit = limitation;

    }

    public Validation() {
        super();
        this.limit = 0;

    }

    @Override
    @SuppressWarnings("AssignmentToMethodParameter")
    public void insertString(int offset, String str, AttributeSet set) throws BadLocationException {
        if (str == null) {
            return;
        } else if ((getLength() + str.length()) <= limit) {
            str = str.toUpperCase();
            super.insertString(offset, str, set);

        }

    }

    public void digitOnly(java.awt.event.KeyEvent e) {

        char c = e.getKeyChar();
        if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE)
                || (c == KeyEvent.VK_DELETE) || (c == KeyEvent.VK_ENTER))) {
            Toolkit.getDefaultToolkit().beep();
            e.consume();

        }
    }

    public void doubleOnly(java.awt.event.KeyEvent e) {

        char c = e.getKeyChar();

        if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE)
                || (c == KeyEvent.VK_DELETE) || (c == '.') || (c == KeyEvent.VK_ENTER))) {
            Toolkit.getDefaultToolkit().beep();
            e.consume();

        }
    }

    public double ParseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber);

            } catch (Exception e) {
                System.out.println(e.toString());
                return 0;
            }
        } else {
            return 0;
        }

    }

    public int ParseInteger(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Integer.parseInt(strNumber);

            } catch (Exception e) {
                System.out.println(e.toString());
                return 0;
            }
        } else {
            return 0;
        }

    }

}
