package com.tk.img;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
 
public class AppUI {
	
	private final ImageCanvas canvas = new ImageCanvas();
	
    private JButton openBtn = new JButton("Select a RAW data file");
    private final JSlider winLevelSlider = new JSlider();
    private final JSlider winWidthSlider = new JSlider();
	
	private short[] pixels;
	private int imgWidth = 512;
    private int imgHeight = 512;
    private int defaultWinWidth = 1 << 12;
	
    public static void main(String[] args) {
    	final AppUI t = new AppUI();
    	
        EventQueue.invokeLater(new Runnable(){
            @Override public void run(){
                t.createUI();
            }
        });
        
    }
    
    public void loadImg(final File f) {
    	@SuppressWarnings("rawtypes")
		SwingWorker worker = new SwingWorker() {
			@Override
			protected Object doInBackground() throws Exception {
				pixels = ImageUtils.read16bitImage(new FileInputStream(f), imgWidth, imgHeight);
				double mean = ImageUtils.means(pixels);
				int levValue = (int)mean;
				int winValue = defaultWinWidth;
		        ImageUtils.setMinAndMax(levValue-winValue/2, levValue + winValue/2);
		        Image img = ImageUtils.createImage(imgWidth, imgHeight, pixels);
		        return new Object[]{img, levValue, winValue};
			}

			@Override
			protected void done() {
				try {
					Object obj = get();
					Object[] values = (Object[])obj;
					Image img = (Image)values[0];
					int levValue = (Integer)values[1];
					int winWidth = (Integer)values[2];
					canvas.setImage(img);
					canvas.setImageWidth(imgWidth);
					canvas.setImageHeight(imgHeight);
					canvas.refresh();
					winLevelSlider.setValue(levValue);
					winWidthSlider.setValue(winWidth);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
        };
        worker.execute();
    }
    
    public void createUI() {
    	final JFrame frame = new JFrame("Load Image Sample");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        doCenter(frame, 800, 600);
        
        Container c = frame.getContentPane();
        c.setLayout(new BorderLayout());
        c.setBackground(Color.white);
        
        //adjusting image width/height when the canvas width/height < 512px 
        canvas.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				if(canvas.getImage() == null) {
					return;
				}
				
				int levValue = winLevelSlider.getValue();
		        int winValue = winWidthSlider.getValue();
		        ImageUtils.setMinAndMax(levValue-winValue/2, levValue + winValue/2);
		        Image image = ImageUtils.createImage(imgWidth, imgHeight, pixels);
		        canvas.setImage(image);
		        
		        int drawingWidth;
		        int drawingHeight;
		        Dimension size = canvas.getSize();
		        drawingWidth = size.width > imgWidth ? imgWidth : size.width;
		        drawingHeight = size.height > imgHeight ? imgHeight : size.height;
		        canvas.setImageWidth(drawingWidth);
				canvas.setImageHeight(drawingHeight);
		        canvas.refresh();
			}
        });
        c.add(canvas, BorderLayout.CENTER);
        c.add(createRightPnl(frame), BorderLayout.EAST);
        
        frame.setVisible(true);
    }
    
    private JPanel createRightPnl(final JFrame frame) {
    	JPanel rightPnl = new JPanel();
    	rightPnl.setBackground(SystemColor.yellow);
        
        GridBagLayout gbLayout = new GridBagLayout();
        gbLayout.columnWidths = new int[]{0, 0};
        gbLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
        gbLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        rightPnl.setLayout(gbLayout);
        
        
        GridBagConstraints gbc= new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        rightPnl.add(openBtn, gbc);
        
		final JLabel levLab = new JLabel("Level:");
		levLab.setHorizontalAlignment(SwingConstants.LEFT);
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(5, 5, 0, 5);
		gbc.gridx = 0;
		gbc.gridy = 1;
		rightPnl.add(levLab, gbc);
		
        gbc= new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 0, 5);
        gbc.gridx = 0;
        gbc.gridy = 2;
        rightPnl.add(winLevelSlider, gbc);
        
        final JLabel winWidthLab = new JLabel("Window:");
		winWidthLab.setHorizontalAlignment(SwingConstants.LEFT);
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(5, 5, 0, 5);
		gbc.gridx = 0;
		gbc.gridy = 3;
		rightPnl.add(winWidthLab, gbc);
		
		gbc= new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(5, 5, 0, 5);
		gbc.gridx = 0; 
		gbc.gridy = 4; 
		rightPnl.add(winWidthSlider, gbc);
        
        openBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser dlg = new JFileChooser();
	            dlg.setDialogTitle("Open");
	            dlg.setFileFilter(new DcmFileFilter("img", ".img"));
	            
	            int result = dlg.showOpenDialog(frame);
	            if (result == JFileChooser.APPROVE_OPTION) {
	            	File file = dlg.getSelectedFile();
	            	//load DICOM raw data
	            	loadImg(file);
	            }
	            
			}
        });
        
        winLevelSlider.setMaximum(defaultWinWidth/2);
        winLevelSlider.setMinimum(0);
        winLevelSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider slider = (JSlider)e.getSource();
				
				int levValue = slider.getValue();
				levLab.setText("Level:" + levValue);
		        int winValue = winWidthSlider.getValue();
		        ImageUtils.setMinAndMax(levValue-winValue/2, levValue + winValue/2);
		        Image image = ImageUtils.createImage(imgWidth, imgHeight, pixels);
		        canvas.setImage(image);
		        canvas.refresh();
			}
        });
        
        winWidthSlider.setMaximum(defaultWinWidth/2);
        winWidthSlider.setMinimum(0);
        winWidthSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider slider = (JSlider)e.getSource();
				int levValue = winLevelSlider.getValue();
				int winValue = slider.getValue();
				winWidthLab.setText("Window:" + winValue);
		        ImageUtils.setMinAndMax(levValue-winValue/2, levValue + winValue/2);
		        Image image = ImageUtils.createImage(imgWidth, imgHeight, pixels);
		        canvas.setImage(image);
		        canvas.refresh();
			}
        });
        
        return rightPnl;
    }
    
    private void doCenter(JFrame frame, int frameWidth, int frameHeight) {
    	Toolkit kit = Toolkit.getDefaultToolkit();
    	Dimension screenSize = kit.getScreenSize();
    	frame.setSize(frameWidth, frameHeight);
    	frame.setLocation(screenSize.width/2 - frameWidth/2, screenSize.height/2 - frameHeight/2);
    }
    

    
    private String getExt(File f) {
		if (f != null) {
			String filename = f.getName();
			int idx = filename.lastIndexOf('.');
			if (idx > 0 && idx < filename.length() - 1) {
				return filename.substring(idx + 1).toLowerCase();
			}
		}
		return null;
    }
    
    class DcmFileFilter extends FileFilter {
    	
    	private String ext;
    	private String desc;
    	
    	public DcmFileFilter(String ext, String desc) {
    		this.ext = ext;
    		this.desc = desc;
    	}

		@Override
		public boolean accept(File f) {
			if(f != null && f.isDirectory()) {
				return true;
			}
			
			String extension = getExt(f);
			if(extension != null && extension.equals(ext)) {
				return true;
			}
			return false;
		}

		@Override
		public String getDescription() {
			return desc;
		}
    	
    }
}