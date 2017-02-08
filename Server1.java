import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
class Server1 implements ActionListener,KeyListener
{
String mtr="";
Socket s;
Frame f;
Button b1,b2,b3;
ServerSocket ss;
int ol=0,j=0,k=0;
List l;
TextField tf;

ObjectOutputStream oos=null;
Panel p;
Server1()
{
f=new Frame("Server");
b1=new Button("Send");
b2=new Button("Save");
b3=new Button("Open");
tf=new TextField();
p=new Panel();
b1.addActionListener(this);
b2.addActionListener(this);
b3.addActionListener(this);

tf.addKeyListener(this);
l=new List();
f.add(tf,BorderLayout.NORTH);
f.add(l);
p.add(b1);
p.add(b2);
p.add(b3);
f.add(p,BorderLayout.SOUTH);
f.setSize(400,400);
f.setVisible(true);

}
public void gobe()
{
try
{

ss=new ServerSocket(2004);
s=ss.accept();
while(true)
{

InputStream in=s.getInputStream();
ObjectInputStream ois=null;
ois=new ObjectInputStream(in);
l.add(""+ois.readObject());

k=l.countItems();
for(j=1;j<k;j++)
{
mtr=l.getItem(j-1);
if(mtr.equals("Client is Typing"))
{
l.remove(j-1);
k=k-1;
}
}

tf.setText("");



}
}
catch(Exception e2)
{
System.out.println(e2.getMessage());
}
}
public static void main(String ar[])throws IOException
{
Server1 ser=new Server1();

ser.gobe();
}
public void actionPerformed(ActionEvent e)
{
if(e.getSource()==b1)
{
try
{

oos=new ObjectOutputStream(s.getOutputStream());
oos.writeObject("Friend:-"+tf.getText());
reset(oos);
l.add("You:-"+tf.getText());
tf.setText("");
ol=0;
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
jf.showSaveDialog(f);
File fl=jf.getSelectedFile();
FileWriter fw=new FileWriter(fl);
PrintWriter pw=new PrintWriter(fw);
k=l.countItems();
for(j=0;j<k;j++)
pw.println(l.getItem(j));
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
jf.showOpenDialog(f);
File fl=jf.getSelectedFile();
FileReader fw=new FileReader(fl);
BufferedReader pw=new BufferedReader(fw);
String strr=pw.readLine();
l.removeAll();
j=0;
while(strr!=null)
{
l.add(strr,j);
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

if(ol==0)
{


oos=new ObjectOutputStream(s.getOutputStream());
oos.writeObject("Server is Typing");
reset(oos);


ol++;
}
}
}
catch(Exception e7)
{System.out.println(e7.getMessage());
}
}
public void reset(ObjectOutputStream ooc)
{
ooc=null;
}

}