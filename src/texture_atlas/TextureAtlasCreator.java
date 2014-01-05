package texture_atlas;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class TextureAtlasCreator {

	private PackingTreeNode packingTree;
	private Dimension imageSize;
	
	public TextureAtlasCreator(Dimension size) { 
		packingTree = new PackingTreeNode(size);
		imageSize = size;
	}
	
	public void pack(ArrayList<Image> images, ArrayList<String> imageIDs) {
		for (int i = 0;i < images.size();i ++) {
			Image image = images.get(i);
			String id = imageIDs.get(i);
			PackingTreeNode node = packingTree.insert(new Dimension(
					image.getWidth(null), image.getHeight(null)));
			node.setImage(image);
			node.setImageID(id);
		}
	}
	
	public void writeAtlasImage(String filePath) {
		BufferedImage image = 
				new BufferedImage(imageSize.width, imageSize.height, BufferedImage.TYPE_INT_ARGB);
		packingTree.render(image.createGraphics());
		File imageFile = new File(filePath);
		if (imageFile.exists())
			imageFile.delete();
		
		try {
			imageFile.createNewFile();
			ImageIO.write(image, "png", imageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeAtlasFile(String filePath) {
		StringBuffer buffer = new StringBuffer();
		packingTree.getAtlasData(buffer);
		File atlasFile = new File(filePath);
		if (atlasFile.exists())
			atlasFile.delete();
		
		try {
			atlasFile.createNewFile();
			PrintWriter out = new PrintWriter(atlasFile);
			out.write(buffer.toString());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		TextureAtlasCreator creator = new TextureAtlasCreator(new Dimension(512, 512));
		ArrayList<Image> images = new ArrayList<Image>();
		ArrayList<String> imageIDs = new ArrayList<String>();
		try {
			File textureFolder = new File("/Users/Nicholas/Desktop/UIComponents");
			for (File image : textureFolder.listFiles()) {
				if (image.isHidden())
					continue;
				
				images.add(ImageIO.read(image));
				imageIDs.add(image.getName().split("\\.")[0]);
			}
		
			creator.pack(images, imageIDs);
			creator.writeAtlasFile("/Users/Nicholas/Desktop/Projects/BlockArt/BlockArt/UIAtlasTexture.atl");
			creator.writeAtlasImage("/Users/Nicholas/Desktop/Projects/BlockArt/BlockArt/UIAtlasTexture.png");
		} catch (IOException ex) { ex.printStackTrace(); }
		
	}
	
	
}
