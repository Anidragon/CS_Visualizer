import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class BinaryPanel extends JPanel {
    private java.util.List<DragNode> nodes;
    private java.util.List<LineComp> lines;
    private DragNode selectedNode;
    private LineComp selectedLine;
    private Point dragOffset;
    // Pop-up menu for editing Nodes
    public JPopupMenu menu = new JPopupMenu("Menu");
    //Pop-up menu for editing Lines
    public JPopupMenu menuLine = new JPopupMenu("Menu");
	public JMenuItem c = new JMenuItem("Color");
	public JMenuItem r = new JMenuItem("Rename");
	public JMenuItem aN = new JMenuItem("Add Child Node");
	public JMenuItem dC = new JMenuItem("Delete Connection");
	public JMenuItem dN = new JMenuItem("Delete This Node");
	public JMenuItem v = new JMenuItem("Set Node Value");
	public JMenuItem cL = new JMenuItem("Set line Color");
    public JMenuItem sT = new JMenuItem("Set line Thickness");

    public BinaryPanel() {
    
    	//list of all nodes and lines currently on the Workspace
        nodes = new java.util.ArrayList<>();
        //list of all the lines currently on the workspace
        lines = new java.util.ArrayList<>();
       
        //options in the pop-up
        menu.add(r);
        menu.add(c);
        menu.add(aN);
        menu.add(dC);
        menu.add(dN);
        menu.add(v);
        
        //Edge menu
        menuLine.add(cL);
        menuLine.add(sT);
        
        
        //renaming a node
        r.addActionListener(new ActionListener() { 
        	  public void actionPerformed(ActionEvent e) { 
        	    
        		  menu.setVisible(false);
	
        		  String name = selectedNode.getName();
        		  name = JOptionPane.showInputDialog("rename the node:", name);
        		  selectedNode.setName(name);
        		  
        		  if(!name.equals(null))
        		  repaint(); 
	
        	  } 
        	} );
        
        
        
        //re-coloring a nodes
        c.addActionListener(new ActionListener() { 
      	  public void actionPerformed(ActionEvent e) { 
      	    
      		  menu.setVisible(false);
      		  //option colors for drop down menu
      		  String[] options = {"White", "Cyan", "Green", "Yellow", "Magenta", "Orange", "Gray"};
      		  DragNode node = selectedNode;
      		  String selection = (String) JOptionPane.showInputDialog(null, "Choose color", "Menu", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
      		  //change color based on choice
      		  if(selection != null)
      		  {
      			  
      		  switch(selection)
      		  {
      		   case "White" : 
      			   
      			   selectedNode.setCol(Color.white);
      			   break;
      			  
      		   case "Cyan" :
      			 selectedNode.setCol(Color.cyan);
    			   break;
      			 
      		   case "Green" :
      			 selectedNode.setCol(Color.green);
    			   break;
      			
      		   case "Yellow" :
      			 selectedNode.setCol(Color.yellow);
    			   break;
      			
      		   case "Magenta" :
      			 selectedNode.setCol(Color.magenta);
    			   break;
    			   
      		   case "Orange":
      			   selectedNode.setCol(Color.orange);
      			   break;
                 
      		   case "Gray" :
      			 selectedNode.setCol(Color.gray);
    			   break;
      		  
      		  }
      		  }
      		  
      		  repaint(); 
	
      	  } 
      	} );
        
        //adding a node
        aN.addActionListener(new ActionListener() { 
      	  public void actionPerformed(ActionEvent e) { 
      		menu.setVisible(false);
      		  int count = 0;
      		  boolean isBinary = true;
      		  for(LineComp line: lines) {
      			  if(line.getStartNode().equals(selectedNode))
      				  count++;
      			  if(count == 2)
      			  {
      				  isBinary = false;
      				  JOptionPane.showMessageDialog(null, "A Binary Tree cannot have more than 2 children per node", "non-binary addition", JOptionPane.ERROR_MESSAGE);
      			  }
      		  }
      		  
      	    if(isBinary)
      	    {
      		  String name = "";
      		  name = JOptionPane.showInputDialog("name for new node:");
      		  
      		  if(name != null) 
      		  {
      			  
      			      
      				  addNode(selectedNode, name, selectedNode.getX() - 20, selectedNode.getY() - 20);
      				  
      		  }
      			  
      		  
      		  repaint(); 
	
      	  } 
      	  }
      	} );
        
        //Deleting Node:
        dN.addActionListener(new ActionListener() { 
      	  public void actionPerformed(ActionEvent e) { 
      	    
      		  menu.setVisible(false);
      		  int choice = JOptionPane.showConfirmDialog(null, "Are you Sure you want to delete " + selectedNode.getName() + " ?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
      		  
      		  if(choice == 0)
      		  {
      			 selectedNode.unLevel();
      		   for(int i = 0; i < lines.size(); i++)
      		   {
      			   if(lines.get(i).getStartNode().equals(selectedNode) || lines.get(i).getEndNode().equals(selectedNode))
      			   {
      				   lines.remove(i);
      				   i--;
      			   }
      		   }
      		   nodes.remove(selectedNode);
      		   
      		   repaint(); 
      		  }
      		  
	
      	  } 
      	} );
        
        //setting value of a node
        v.addActionListener(new ActionListener() { 
        	  public void actionPerformed(ActionEvent e) { 
        	    
        		  menu.setVisible(false);
        			
        		  String value = selectedNode.getValue();
        		  value = JOptionPane.showInputDialog("rename the node:", value);
        		  selectedNode.setValue(value);
        		  
        		  if(!value.equals(null))
        		  repaint(); 
        		  
  	
        	  } 
        	} );
        
        cL.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuLine.setVisible(false);
                // Option colors for drop-down menu
                String[] options = {"Black", "Cyan", "Green", "Yellow", "Magenta", "Orange", "Gray"};
                String selection = (String) JOptionPane.showInputDialog(null, "Choose color", "Menu",
                        JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                // Change color based on choice
                if(selection != null)
                {
                switch (selection) {
                    case "Black":
                        selectedLine.setCol(Color.black);
                        break;
                        
                    case "Cyan":
                        selectedLine.setCol(Color.cyan);
                        break;
                        
                    case "Green":
                        selectedLine.setCol(Color.green);
                        break;
                        
                    case "Yellow":
                        selectedLine.setCol(Color.yellow);
                        break;
                        
                    case "Magenta":
                        selectedLine.setCol(Color.magenta);
                        break;
                        
                    case "Orange":
                    	selectedNode.setCol(Color.orange);
                        break;
                        
                    case "Gray":
                        selectedLine.setCol(Color.gray);
                        break;
                   }
                }
                repaint();
            }
        });

        
        sT.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	menu.setVisible(false);
            	 String[] options = {"Light", "Bold", "Ultra-Bold"};
                String directed = "";
                directed = (String) JOptionPane.showInputDialog(null, "Choose Edge Specifics", "Menu",
                        JOptionPane.OK_OPTION, null, options, options[0]);
                if (directed != null)
                {
                	switch(directed)
                	{
                	 case "Light":
                		 selectedLine.setThick(1);
                		 break;
                	
                	 case "Bold":
                		 selectedLine.setThick(3);
                		 break;
                		 
                	 case "Ultra-Bold":
                		 selectedLine.setThick(5);
                		 break;
                	
                	
                	
                	}
                   
                    
                }
                repaint();
                
            }
        });
        
        
        //allowing for pop-up menu to become active when right click event occurs on node and line
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                selectNode(e.getX(), e.getY());
            }

            public void mouseReleased(MouseEvent e) {
                selectedNode = null;
            }
            
            public void mouseClicked(MouseEvent e)
            {
            	selectNode(e.getX(), e.getY());
            	if(e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON2)
            	{
            		menu.setVisible(false);
            	}
            	
            	if(selectedNode != null) {
            
            	if(e.getButton() == MouseEvent.BUTTON3)
            	{
            		
                            menu.show(null, selectedNode.getX(), selectedNode.getY());
            		
            			}
            		}
            		
            	}
        });
        
        //allows for dragging of nodes
        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (selectedNode != null) {
                    int newX = e.getX() - dragOffset.x;
                    int newY = e.getY() - dragOffset.y;
                    selectedNode.setX(newX);
                    selectedNode.setY(newY);
                   
                    repaint();
                  for(LineComp line: lines)
                  {
                	  if(line.getStartNode().equals(selectedNode)||line.getEndNode().equals(selectedNode))
                	  {
                		  int p1 = line.Dist(line.getStartNode().getX() + 7, line.getStartNode().getY() + 7, line.getEndNode().getX() + 43, line.getEndNode().getY() + 43);
                		  int p2 = line.Dist(line.getStartNode().getX(), line.getStartNode().getY() + 25, line.getEndNode().getX() + 50, line.getEndNode().getY() + 25);
                		 int p3 = line.Dist(line.getStartNode().getX() + 7, line.getStartNode().getY() + 43, line.getEndNode().getX() + 43, line.getEndNode().getY() + 7);
                		  int p4 = line.Dist(line.getStartNode().getX() + 25, line.getStartNode().getY() + 50, line.getEndNode().getX() + 25, line.getEndNode().getY());
                		  int p5 = line.Dist(line.getStartNode().getX() + 43, line.getStartNode().getY() + 43, line.getEndNode().getX() + 7, line.getEndNode().getY() + 7);
                		  int p6 = line.Dist(line.getStartNode().getX() + 50, line.getStartNode().getY() + 25, line.getEndNode().getX() + 0, line.getEndNode().getY() + 25);
                		 int p7 = line.Dist(line.getStartNode().getX() + 43, line.getStartNode().getY() + 7, line.getEndNode().getX() + 7, line.getEndNode().getY() + 43);
                		  int p8 = line.Dist(line.getStartNode().getX() + 25, line.getStartNode().getY(), line.getEndNode().getX() + 25, line.getEndNode().getY() + 50);
                		  int dist = line.Dist(line.getStartNode().getX(), line.getStartNode().getY(), line.getEndNode().getX(), line.getEndNode().getY());
                		  
                		  int pf = Math.min(Math.min(Math.min(Math.min(dist, p1), Math.min(p2, p3)),Math.min(Math.min(p4, p5), Math.min(p6, p7))), p8);
                		 
                		  if(pf == p1)
                			  line.setPreset(1);
                		  else if( pf == p2)
                			  line.setPreset(2);
                		  else if( pf == p3)
                			  line.setPreset(3);
                		  else if( pf == p4)
                			  line.setPreset(4);
                		  else if( pf == p5)
                			  line.setPreset(5);
                		  else if( pf == p6)
                			  line.setPreset(6);			  
                		  else if( pf == p7)
                			  line.setPreset(7);				  
                		  else if(pf == p8)
                			  line.setPreset(8);
                			  
                	  }
                	  
                  }
                }
            }
        });

        //triggers selection of nodes and lines, and updates selections when new node or line is selected
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                selectNode(e.getX(), e.getY());
                selectLine(e.getX(), e.getY());
            }

            public void mouseReleased(MouseEvent e) {
                selectedNode = null;
                selectedLine = null;
            }

            public void mouseClicked(MouseEvent e) {
                selectNode(e.getX(), e.getY());
                selectLine(e.getX(), e.getY());
                if (e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON2) {
                    menu.setVisible(false);
                    menuLine.setVisible(false);
                }

                if (selectedNode != null) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        menu.show(null, selectedNode.getX(), selectedNode.getY());
                    }
                }
                else if(selectedLine != null) {
                	menuLine.show(null, (selectedLine.getEndX() + selectedLine.getStX())/2, (selectedLine.getEndY() + selectedLine.getStY())/2 );
                }
            }
        });
    }

    //addNode helper method to initialize new node with connecting Line
    public void addNode(DragNode startNode, String name, int x, int y) {
        nodes.add(new DragNode(name, x, y));
        lines.add(new LineComp("", startNode.getX() + 25, startNode.getY() + 50, nodes.get(nodes.size()-1).getX() + 25, nodes.get(nodes.size()-1).getY()));
        lines.get(lines.size()-1).setEndNode(nodes.get(nodes.size()-1));
        lines.get(lines.size()-1).setStartNode(startNode);
        nodes.get(nodes.size()-1).setDisplay(startNode.getDisplay());
        nodes.get(nodes.size()-1).setLevel(startNode.getLevel()+1);
        if(selectedNode.getChildren().size() == 1)
		  {
			  if(Integer.parseInt(selectedNode.getChildren().get(0).getValue()) >= 0)
			  {
				  selectedNode.getChildren().get(0).getOrient().set(0, "R");
				  nodes.get(nodes.size()-1).getOrient().add("L");
			  }
			  
			  else
			  {
				  selectedNode.getChildren().get(0).getOrient().set(0, "L");
				  nodes.get(nodes.size()-1).getOrient().add("R");
			  }
		  }
        
        else if(selectedNode.getChildren().size() == 0)
        {
        	if(Integer.parseInt(selectedNode.getValue()) >= 0)
        	{
        		nodes.get(nodes.size()-1).getOrient().add("L");
        	}
        	
        	else
        	{
        		nodes.get(nodes.size()-1).getOrient().add("R");
        	}
        		
        }
        startNode.getChildren().add(nodes.get(nodes.size()-1));
        repaint();
    }
    
    //basic add node with no line drawn
    public void addNode(String name, int x, int y) {
        nodes.add(new DragNode(name, x, y));
        repaint();
    }

    //changing display type of all nodes in the scene
    public void changeDisplay(String dis)
    {
    	for(DragNode node : nodes)
    	{
    		node.setDisplay(dis);
    	}
    	
    	repaint();
    }
    
    //paint function called whenever workspace is updated using the repaint() method
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
       
     
        for (DragNode node : nodes) 
        {
        	if(node.getX() >= 1485)
        	{
        		node.setX(1485);
        	}
        	else if(node.getX() <= 0)
        	{
        		node.setX(0);
        	}
        	
        	if(node.getY() >= 720)
        	{
        		node.setY(720);
        	}
        	else if(node.getY() <= 0)
        	{
        		node.setY(0);
        	}
        	
        				
        	//drawing the shape of the node
            int x = node.getX();
            int y = node.getY();
            g2.setColor(node.getColor());
            g2.fillOval(x, y, 50, 50);
            g2.setColor(Color.BLACK);
            g2.drawOval(x, y, 50, 50);
            
            //displaying the correct value based on the display type of the nodes
            switch(node.getDisplay()){
            case "names" :
            	g2.drawString(node.getName(), x + 20, y + 30);
            	break;
            case "values":
            	g2.drawString(node.getValue(), x + 24, y + 30);
            	break;
            case "levels":
            	g2.drawString(node.getLevel()+"", x + 24, y + 30);
            	break;
            	
            	
            }
        
        }
        
       //drawing all the lines with provided color and thickness, and setting proper orientation preset
        for (LineComp line : lines){
        	g2.setColor(line.getColor());
        	int startX = 0;
    		int startY = 0;
    		int endX = 0;
    		int endY = 0;
        	switch(line.getPreset()) {
        	
        	case 1:
        	    startX = line.getStartNode().getX() + 7;
        	    startY = line.getStartNode().getY() + 7;
        		endX = line.getEndNode().getX() + 43;
        		endY = line.getEndNode().getY() + 43;
        		break;
        		
        	case 2:
        		startX = line.getStartNode().getX();
        	    startY = line.getStartNode().getY() + 25;
        		endX = line.getEndNode().getX() + 50;
        		endY = line.getEndNode().getY() + 25;
        		break;
        		
        	case 3:
        		startX = line.getStartNode().getX() + 7;
        	    startY = line.getStartNode().getY() + 43;
        		endX = line.getEndNode().getX() + 43;
        		endY = line.getEndNode().getY() + 7;
        		break;
        		
        	case 4:
        		startX = line.getStartNode().getX() + 25;
        		startY = line.getStartNode().getY() + 50;
        		endX = line.getEndNode().getX() + 25;
        		endY = line.getEndNode().getY();
        		break;
        		
        	case 5:
        		startX = line.getStartNode().getX() + 43;
        	    startY = line.getStartNode().getY() + 43;
        		endX = line.getEndNode().getX() + 7;
        		endY = line.getEndNode().getY() + 7;
        		break;
        		
        	case 6:
        		startX = line.getStartNode().getX() + 50;
        	    startY = line.getStartNode().getY() + 25;
        		endX = line.getEndNode().getX();
        		endY = line.getEndNode().getY() + 25;
        		break;
        		
        	case 7:
        		startX = line.getStartNode().getX() + 43;
        	    startY = line.getStartNode().getY() + 7;
        		endX = line.getEndNode().getX() + 7;
        		endY = line.getEndNode().getY() + 43;
        		break;
        		
        	case 8:
        		startX = line.getStartNode().getX() + 25;
        	    startY = line.getStartNode().getY();
        		endX = line.getEndNode().getX() + 25;
        		endY = line.getEndNode().getY() + 50;
        		break;
            
        	}
            // Draw a line between nodes
            g2.setStroke(new BasicStroke(line.getThick()));
            g2.drawLine(startX, startY, endX, endY);
        }
        
    }
    
    //helper method run to figure out what node is currently selected
    private void selectNode(int x, int y) 
    {
        for (DragNode node : nodes) {
            int nodeX = node.getX();
            int nodeY = node.getY();
            if (x >= nodeX && x <= nodeX + 50 && y >= nodeY && y <= nodeY + 50) {
                selectedNode = node;
                dragOffset = new Point(x - nodeX, y - nodeY);
                break;
            }
            
            else
            {
            	selectedNode = null;
            }
        }
    }
    
  //helper method run to figure out what line is currently selected
    private void selectLine(int x, int y) {

        // Check if a line is selected
        for (LineComp line : lines) {
            if (line.isInsideLine(x, y)) {
                selectedLine = line;
                break;
            } 
            else {
                selectedLine = null;
            }
        }
    }
    
    //method for re-aligning all lines to proper preset
    public void align()
    {
    	for(LineComp line : lines) {
    	int p1 = line.Dist(line.getStartNode().getX() + 7, line.getStartNode().getY() + 7, line.getEndNode().getX() + 43, line.getEndNode().getY() + 43);
		  int p2 = line.Dist(line.getStartNode().getX(), line.getStartNode().getY() + 25, line.getEndNode().getX() + 50, line.getEndNode().getY() + 25);
		 int p3 = line.Dist(line.getStartNode().getX() + 7, line.getStartNode().getY() + 43, line.getEndNode().getX() + 43, line.getEndNode().getY() + 7);
		  int p4 = line.Dist(line.getStartNode().getX() + 25, line.getStartNode().getY() + 50, line.getEndNode().getX() + 25, line.getEndNode().getY());
		  int p5 = line.Dist(line.getStartNode().getX() + 43, line.getStartNode().getY() + 43, line.getEndNode().getX() + 7, line.getEndNode().getY() + 7);
		  int p6 = line.Dist(line.getStartNode().getX() + 50, line.getStartNode().getY() + 25, line.getEndNode().getX() + 0, line.getEndNode().getY() + 25);
		 int p7 = line.Dist(line.getStartNode().getX() + 43, line.getStartNode().getY() + 7, line.getEndNode().getX() + 7, line.getEndNode().getY() + 43);
		  int p8 = line.Dist(line.getStartNode().getX() + 25, line.getStartNode().getY(), line.getEndNode().getX() + 25, line.getEndNode().getY() + 50);
		  int dist = line.Dist(line.getStartNode().getX(), line.getStartNode().getY(), line.getEndNode().getX(), line.getEndNode().getY());
		  
		  int pf = Math.min(Math.min(Math.min(Math.min(dist, p1), Math.min(p2, p3)),Math.min(Math.min(p4, p5), Math.min(p6, p7))), p8);
		 
		  if(pf == p1)
			  line.setPreset(1);
		  else if( pf == p2)
			  line.setPreset(2);
		  else if( pf == p3)
			  line.setPreset(3);
		  else if( pf == p4)
			  line.setPreset(4);
		  else if( pf == p5)
			  line.setPreset(5);
		  else if( pf == p6)
			  line.setPreset(6);			  
		  else if( pf == p7)
			  line.setPreset(7);				  
		  else if(pf == p8)
			  line.setPreset(8);
			  
	  }
    }
    
    //getter method for the list of nodes in the workspace
    public java.util.List<DragNode> getNodeList()
    {
    	return nodes;
    }
    
  //getter method for the list of lines in the workspace
    public java.util.List<LineComp> getLineList()
    {
    	return lines;
    }
    
   
    
    
}
