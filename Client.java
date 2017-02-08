import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
class Client implements ActionListener,KeyListener
{
String mtr="";
Socket s;
Frame f1;
Button b1,b2,b3;
List lst;
TextField ta;
Panel p;
int lo=0,j=0,k=0;

Client()
{

f1=new Frame("Client");
b1=new Button("Send");
b2=new Button("Save");
b3=new Button("Open");
lst=new List();
ta=new TextField();
p=new Panel();
b1.addActionListener(this);
b2.addActionListener(this);
b3.addActionListener(this);

ta.addKeyListener(this);

f1.add(ta,BorderLayout.NORTH);
f1.add(lst);
p.add(b1);
p.add(b2);
p.add(b3);
f1.add(p,BorderLayout.SOUTH);
f1.setSize(400,400);
f1.setVisible(true);
}
public void go()
{
try
{
s=new Socket("localhost",2004);

while(true)
{
ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
lst.add(""+ois.readObject());
k=lst.countItems();
for(j=1;j<k;j++)
{
mtr=lst.getItem(j-1);
if(mtr.equals("Server is Typing"))
{
lst.remove(j-1);
k=k-1;
}
}

ta.setText("");
}
}
catch(Exception e2)
{
System.out.println(e2.getMessage());
}


}


public static void main(String ar[])throws IOException
{
Client cl=new Client();
cl.go();
}
public void actionPerformed(ActionEvent e)
{
if(e.getSource()==b1)
{
try
{
ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
oos.writeObject("Friend:-"+ta.getText());
lst.add("You:-"+ta.getText());
ta.setText("");
lo=0;
}
catch(Exception e1)
{
System.out.println(e1.getMessage());
}
}
if(e.getSource()==b2)
{
try
{
javax.swing.JFileChooser jf=new javax.swing.JFileChooser();
jf.showSaveDialog(f1);
File fl=jf.getSelectedFile();
FileWriter fw=new FileWriter(fl);
PrintWriter pw=new PrintWriter(fw);
k=lst.countItems();
for(j=0;j<k;j++)
pw.println(lst.getItem(j));
pw.close();
fw.close();
}
catch(Exception e1)
{
System.out.println(e1.getMessage());
}
}
if(e.getSource()==b3)
{
try
{
javax.swing.JFileChooser jf=new javax.swing.JFileChooser();
jf.showOpenDialog(f1);
File fl=jf.getSelectedFile();
FileReader fw=new FileReader(fl);
BufferedReader pw=new BufferedReader(fw);
String strr=pw.readLine();
lst.removeAll();
j=0;
while(strr!=null)
{
lst.add(strr,j);
j++;
strr=pw.readLine();
}
pw.close();
fw.close();
}
catch(Exception e1)
{
System.out.println(e1.getMessage());
}
}

}
public void keyPressed(KeyEvent e)
{

}
public void keyReleased(KeyEvent e)
{

}
public void keyTyped(KeyEvent e)
{
try
{
if(e.getKeyChar()!='\0')
{
if(lo==0)
{
ObjectOutputStream oosc=new ObjectOutputStream(s.getOutputStream());
oosc.writeObject("Client is Typing");
lo++;
}
}

}
catch(Exception e7)
{System.out.println(e7.getMessage());
}

}
}