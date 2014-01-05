package texture_atlas;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

public class PackingTreeNode {

	private PackingTreeNode[] children;
	private Rectangle rectangle;
	    
	private String imageId;
	private Image image;
	
	public PackingTreeNode(Dimension dim) { rectangle = new Rectangle(dim); }
	
	public PackingTreeNode() { }
	
	public PackingTreeNode insert(Dimension dim) {
	    if (children != null) {
	    	PackingTreeNode newNode = children[0].insert(dim);
	        if (newNode != null) 
	        	return newNode;
	        
	        return children[1].insert(dim);
	    } else {
	        if (imageId != null) 
	        	return null;

	        if (rectangle.width < dim.width || rectangle.height < dim.height)
	            return null;

	        if (rectangle.width == dim.width && rectangle.height == dim.height) 
	            return this;
	        
	        children = new PackingTreeNode[2];
	        children[0] = new PackingTreeNode();
	        children[1] = new PackingTreeNode();
	        
	        int dw = rectangle.width - dim.width;
	        int dh = rectangle.height - dim.height;
	        
	        if (dw > dh) {
	        	children[0].rectangle = new Rectangle(rectangle.x, rectangle.y, 
	        			dim.width, rectangle.height);
	            children[1].rectangle = new Rectangle(rectangle.x + dim.width, rectangle.y, 
	            		dw, rectangle.height);
	        } else {
	            children[0].rectangle = new Rectangle(rectangle.x, rectangle.y, 
	            		rectangle.width, dim.height);
	            children[1].rectangle = new Rectangle(rectangle.x, rectangle.y + dim.height, 
	            		rectangle.width, dh);
	        }
	        
	        return children[0].insert(dim);
	    }
	}
	
	public void setImage(Image img) {
		image = img;
	}
	
	public void setImageID(String id) {
		imageId = id;
	}
	
	public void getAtlasData(StringBuffer buffer) {
		if (imageId != null) {
			buffer.append(imageId + ":");
			buffer.append(rectangle.x + ":");
			buffer.append((rectangle.y + rectangle.height) + ":");
			buffer.append((rectangle.x + rectangle.width) + ":");
			buffer.append(rectangle.y + ":");
		}
		
		if (children != null) {
			children[0].getAtlasData(buffer);
			children[1].getAtlasData(buffer);
		}
	}
	
	public void render(Graphics2D g) {
		if (imageId != null) {
			g.drawImage(image, rectangle.x, rectangle.y, 
					rectangle.width, rectangle.height, null);
		}
		
		if (children != null) {
			children[0].render(g);
			children[1].render(g);
		}
	}
	
}
