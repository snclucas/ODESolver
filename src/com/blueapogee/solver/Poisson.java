package com.blueapogee.solver;
	
	/* Poisson.java */
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JFrame;

	public class Poisson extends Applet implements ItemListener, ActionListener{
	   int width, height;
	   int width1, height1;
	   int M, N;
	   float xmin = -0.2f, xmax= 1.2f;
	   float ymin = -0.2f, ymax= 1.2f;
	   float xleft = 0.0f, xright = 1.0f;
	   float ylower = 0.0f, yupper = 1.0f;
	   float h;
	   String s0="Jacobi";
	   String s1, s2;
	   Button bStart, bClear, bExit;
	   Choice methodChoice;
	   Label l1 = new Label(" Method =");
	   Label l2 = new Label(" M =");
	   Label l3 = new Label(" N =");
	   TextField box1 = new TextField(5);
	   TextField box2 = new TextField(5);
	   int istate = 1;
	   int M0 = 50;
	   int N0 = 100;
	   int istep;
	   int istep0 = 20;
	   float err0, err1;
	   
	   float[][] rho = new float[M0+1][M0+1];

	   
	   
	   public static void main(String args[]) {
		
		   JFrame myFrame = new JFrame("Applet Holder"); // create frame with title
		   Poisson p = new Poisson();
		   
		   // add applet to the frame
		    myFrame.add(p, BorderLayout.CENTER);
		    myFrame.pack(); // set window to appropriate size (for its elements)
		    myFrame.setVisible(true); // usual step to make frame visible
	   
	   }
	   
	   public Poisson() {
		   init();
	   }

	   public void init(){
	      width  = getSize().width;
	      width1 = width;
	      width  = (int)((float) width * 2.0f / 3.0f);
	      height = getSize().height;
	      height1= height;
	      height = height - 50;
	      setBackground(Color.white);
	      setForeground(Color.black);
	      bStart = new Button("Start");
	      add(bStart);
	      bStart.addActionListener(this);
	      add(l1);
	      methodChoice = new Choice();
	      methodChoice.addItem("Jacobi");
	      methodChoice.addItem("Gauss-Seidel");
	      methodChoice.addItem("SOR");
	      add(methodChoice);
	      methodChoice.addItemListener(this);
	      add(l2);
	      add(box1);
	      add(l3);
	      add(box2);
	      String s1 = Integer.toString(M0);
	      box1.setText(s1);
	      String s2 = Integer.toString(N0);
	      box2.setText(s2);
	      
	      
	      for (int i=0; i<=M0; i++ )
	      {
	         for (int j=0; j<=M0; j++ )
	            rho[i][j] = 0.0f;
	      }
	      
	      rho[20][20]=-100;
	      rho[30][30]=-100;
	      
	   }

	   
	      
	   public void itemStateChanged(ItemEvent e){
	      if (e.getSource() == methodChoice)
	         s0 = methodChoice.getSelectedItem();
	   }

	   public void actionPerformed(ActionEvent e){
	      if (e.getSource() == bStart)
	      {
	         repaint();
	      }
	   }

	   public void paint(Graphics g){
	      g.setColor(Color.black);
	      g.drawLine(convX(xmin), convY(0.0f), convX(xmax), convY(0.0f));
	      g.drawLine(convX(0.0f), convY(ymin), convX(0.0f), convY(ymax));
	   }

	   public void update(Graphics g){
		   super.update(g);
	      int r2=4;

	      if ( istate == 1 )
	      {
	         g.setColor(Color.white);
	         g.fillRect( 0, 50, getSize().width, getSize().height );
	         g.setColor(Color.black);
	         g.drawLine(convX(xmin), convY(0.0f), convX(xmax), convY(0.0f));
	         g.drawLine(convX(0.0f), convY(ymin), convX(0.0f), convY(ymax));
	      }

	      if ( istate == 1 )
	      {
	         plotGraph(g);
	      }

	      if ( istate == 2)
	      {
	         String t1 = box1.getText();
	         M = Integer.parseInt(t1);
	         String t2 = box2.getText();
	         N = Integer.parseInt(t2);
	      }

	      if ( istate == 2 )
	      {
	         if (s0 == "Jacobi")
	            solveJacobi(g);
	         if (s0 == "Gauss-Seidel")
	            solveGaussSeidel(g);
	         if (s0 == "SOR")
	            solveSOR(g);
	      }

	      istate++;
	      if ( istate == 3 ) istate = 1;

	   }

	   private void plotGraph(Graphics g) {
	      int i;
	      float x0, y0, x1, y1;
	      float temp;
	      x0 = 0.0f;
	      y0 = 1.0f;
	      x1 = 1.0f;
	      y1 = 0.0f;
	      temp = 0.0f;
	      plotSquare(g, x0, y0, x1, y1, temp);
	   }

	   private void plotSquare(Graphics g, float x0, float y0, float x1, float y1, float temp) {
	      int rr=0, gg=0, bb=0;
	      float temp1;
	      int hx, hy;

	      temp1 = temp;
	      if ( temp1 < 0.0f ) temp1 = 0.0f;
	      if ( temp1 > 1.0f ) temp1 = 1.0f;

	      if ( temp1 < 0.25f )
	      {
	         rr = 0;
	         gg = (int)(960.0f*temp1);
	         bb = 240;
	      }
	      if ( temp1 >= 0.25f && temp < 0.5f )
	      {
	         rr = 0;
	         gg = 240;
	         bb = 240-(int)(960.0f*(temp1-0.25f));
	      }
	      if ( temp1 >= 0.5f && temp < 0.75f )
	      {
	         rr = (int)(960.0f*(temp1-0.5f));
	         gg = 240;
	         bb = 0;
	      }
	      if ( temp1 >= 0.75f )
	      {
	         rr = 240;
	         gg = 240-(int)(960.0f*(temp1-0.75f));
	         bb = 0;  
	      }

	      g.setColor(new Color(rr,gg,bb));
	      hx = convX(x1)-convX(x0);
	      hy = convY(y1)-convY(y0);
	      g.fillRect(convX(x0), convY(y0), hx, hy);
	   }

	   private void solveJacobi(Graphics g) {
	      int i, j, k;
	      float x0, y0, x1, y1, xi, yj;
	      h = (xright-xleft)/(float)M;
	      float[][] u0 = new float[M+1][M+1];
	      float[][] u1 = new float[M+1][M+1];
	      float temp;
	      float errtmp;
	      
	      g.drawString("N", 600, 75);
	      g.drawString("Error", 650, 75);

	      for ( i=0; i<=M; i++ )
	      {
	         for ( j=0; j<=M; j++ )
	            u0[i][j] = 0.0f;
	      }

	      for ( k=1; k<=N; k++ )
	      {
	         err1 = 0.0f;
	         for ( j=1; j<M; j++ )
	         {
	            yj = (float)j*h;
	            y0 = yj + 0.51f*h;
	            y1 = yj - 0.51f*h;
	            for ( i=1; i<M; i++ )
	            {
	               xi = (float)i*h;
	               u1[i][j] = 0.25f*(u0[i+1][j]+u0[i-1][j]+u0[i][j+1]+u0[i][j-1]) - 0.25f*h*h*func(xi, yj);
	               x0 = xi - 0.51f*h;
	               x1 = xi + 0.51f*h;
	               temp = u1[i][j]*14.0f;
	               plotSquare(g, x0, y0, x1, y1, temp);
	               errtmp = (float)Math.abs((u1[i][j]-u0[i][j])/(0.25*h*h));
	               if ( errtmp > err1 )
	                  err1 = errtmp;
	            }
	            u1[0][j] = 0.0f;
	            u1[M][j] = 0.0f;
	         }
	         for ( i=0; i<=M; i++ )
	         {
	            u1[i][0] = 0.0f;
	            u1[i][M] = 0.0f;
	         }

	         for ( i=0; i<=M; i++ )
	         {
	            for ( j=0; j<=M; j++ )
	               u0[i][j] = u1[i][j];
	         }
	         if ( k%100 == 0 ){
	            g.drawString(""+k+"", 600, 75+k/4);
	            g.drawString(""+err1+"", 650, 75+k/4);
	         }
	      }
	   }

	   private void solveGaussSeidel(Graphics g) {
	      int i, j, k;
	      float x0, y0, x1, y1, xi, yj;
	      h = (xright-xleft)/(float)M;
	      float[][] u0 = new float[M+1][M+1];
	      float[][] u1 = new float[M+1][M+1];
	      float temp;
	      float errtmp;

	      g.drawString("N", 600, 75);
	      g.drawString("Error", 650, 75);

	      for ( i=0; i<=M; i++ )
	      {
	         for ( j=0; j<=M; j++ )
	            u0[i][j] = 0.0f;
	      }

	      for ( k=1; k<=N; k++ )
	      {
	         err1 = 0.0f;
	         for ( j=1; j<M; j++ )
	         {
	            yj = (float)j*h;
	            y0 = yj + 0.51f*h;
	            y1 = yj - 0.51f*h;
	            for ( i=1; i<M; i++ )
	            {
	               xi = (float)i*h;
	              // u1[i][j] = 0.25f*(u0[i+1][j]+u1[i-1][j]+u0[i][j+1]+u1[i][j-1]) - 0.25f*h*h*func(xi, yj);
	               u1[i][j] = 0.25f*(u0[i+1][j]+u1[i-1][j]+u0[i][j+1]+u1[i][j-1]) - 0.25f*h*h*ifunc(i, j);
	               x0 = xi - 0.51f*h;
	               x1 = xi + 0.51f*h;
	               temp = u1[i][j]*14.0f;
	               plotSquare(g, x0, y0, x1, y1, temp);
	               //errtmp = (float)Math.abs((u0[i+1][j]+u0[i-1][j]+u0[i][j+1]+u0[i][j-1]-4.0f*u0[i][j])/(h*h) - func(xi, yj));
	               errtmp = (float)Math.abs((u0[i+1][j]+u0[i-1][j]+u0[i][j+1]+u0[i][j-1]-4.0f*u0[i][j])/(h*h) - ifunc(i, j));
	               if ( errtmp > err1 )
	                  err1 = errtmp;
	            }
	            u1[0][j] = 0.0f;
	            u1[M][j] = 0.0f;
	         }
	         for ( i=0; i<=M; i++ )
	         {
	            u1[i][0] = 0.0f;
	            u1[i][M] = 0.0f;
	         }

	         for ( i=0; i<=M; i++ )
	         {
	            for ( j=0; j<=M; j++ )
	               u0[i][j] = u1[i][j];
	         }
	         if ( k%100 == 0 ){
	            g.drawString(""+k+"", 600, 75+k/4);
	            g.drawString(""+err1+"", 650, 75+k/4);
	         }
	      }
	   }

	   private void solveSOR(Graphics g) {
	      int i, j, k;
	      float x0, y0, x1, y1, xi, yj;
	      h = (xright-xleft)/(float)M;
	      float[][] u0 = new float[M+1][M+1];
	      float[][] u1 = new float[M+1][M+1];
	      float temp;
	      float errtmp;
	      float omega=1.9f;

	      g.drawString("N", 600, 75);
	      g.drawString("Error", 650, 75);
	      
	      for ( i=0; i<=M; i++ )
	      {
	         for ( j=0; j<=M; j++ )
	            u0[i][j] = 0.0f;
	      }

	      for ( k=1; k<=N; k++ )
	      {
	         err1 = 0.0f;
	         for ( j=1; j<M; j++ )
	         {
	            yj = (float)j*h;
	            y0 = yj + 0.51f*h;
	            y1 = yj - 0.51f*h;
	            for ( i=1; i<M; i++ )
	            {
	               xi = (float)i*h;
	               u1[i][j] = u0[i][j] + omega*(0.25f*(u0[i+1][j]+u1[i-1][j]+u0[i][j+1]+u1[i][j-1]-4.0f*u0[i][j]) - 0.25f*h*h*func(xi, yj));
	               x0 = xi - 0.51f*h;
	               x1 = xi + 0.51f*h;
	               temp = u1[i][j]*14.0f;
	               plotSquare(g, x0, y0, x1, y1, temp);
	               errtmp = (float)Math.abs((u0[i+1][j]+u0[i-1][j]+u0[i][j+1]+u0[i][j-1]-4.0f*u0[i][j])/(h*h) - func(xi, yj));
	               if ( errtmp > err1 )
	                  err1 = errtmp;
	            }
	            u1[0][j] = 0.0f;
	            u1[M][j] = 0.0f;
	         }
	         for ( i=0; i<=M; i++ )
	         {
	            u1[i][0] = 0.0f;
	            u1[i][M] = 0.0f;
	         }

	         for ( i=0; i<=M; i++ )
	         {
	            for ( j=0; j<=M; j++ )
	               u0[i][j] = u1[i][j];
	         }
	         if ( k%100 == 0 ){
	            g.drawString(""+k+"", 600, 75+k/4);
	            g.drawString(""+err1+"", 650, 75+k/4);
	         }
	      }
	   }

	   private float func(float x, float y){
	      float func = 0.0f;
	      func = -1.0f;
	      func = rho[(int)x][(int)y];
	      return func;
	   }
	   
	   private float ifunc(int x, int y){

		      return rho[x][y];
		   }

	   private int convX(float x){
	      return (int)((float)width*(x-xmin)/(xmax-xmin));
	   }
	   private int convY(float y){
	      return (int)((float)height*(ymax-y)/(ymax-ymin))+50;
	   }
	}
	


