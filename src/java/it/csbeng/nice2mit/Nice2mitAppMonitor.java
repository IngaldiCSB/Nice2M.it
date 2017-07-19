package it.csbeng.nice2mit;

import it.csbeng.nice2mit.utils.CarmineHibernateUtil;
import java.util.Date;
import org.hibernate.Session;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gennaro
 */
public class Nice2mitAppMonitor extends javax.swing.JFrame
{

    private Session sess = null;
    /**
     * Creates new form Nice2mitAppMonitor
     */
    public Nice2mitAppMonitor()
    {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        StartupBtn = new javax.swing.JButton();
        ShutdownBtn = new javax.swing.JButton();
        LogAreaPane = new javax.swing.JScrollPane();
        LogArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Nice2m.it App");

        StartupBtn.setText("Startup");
        StartupBtn.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                StartupBtnEvt(evt);
            }
        });

        ShutdownBtn.setText("Shutdown");
        ShutdownBtn.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                ShutdownBtnEvt(evt);
            }
        });

        LogArea.setColumns(20);
        LogArea.setRows(5);
        LogAreaPane.setViewportView(LogArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ShutdownBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(StartupBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(LogAreaPane, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(StartupBtn)
                .addGap(18, 18, 18)
                .addComponent(ShutdownBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(LogAreaPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void StartupBtnEvt(java.awt.event.MouseEvent evt)//GEN-FIRST:event_StartupBtnEvt
    {//GEN-HEADEREND:event_StartupBtnEvt
        sess = CarmineHibernateUtil.getSessionFactory().openSession();
        printLog("server start");
        
    }//GEN-LAST:event_StartupBtnEvt

    private void ShutdownBtnEvt(java.awt.event.MouseEvent evt)//GEN-FIRST:event_ShutdownBtnEvt
    {//GEN-HEADEREND:event_ShutdownBtnEvt
        sess.close();
        printLog("server down");

    }//GEN-LAST:event_ShutdownBtnEvt

    private void printLog(String txt)
    {
        LogArea.append("[" + new Date ().toString() + "] " + txt + "\n");
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(Nice2mitAppMonitor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(Nice2mitAppMonitor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(Nice2mitAppMonitor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(Nice2mitAppMonitor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new Nice2mitAppMonitor().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea LogArea;
    private javax.swing.JScrollPane LogAreaPane;
    private javax.swing.JButton ShutdownBtn;
    private javax.swing.JButton StartupBtn;
    // End of variables declaration//GEN-END:variables
}
