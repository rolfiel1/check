/**
 *Title:  
 *Description: 
 *Copyright: WOT 
 *Company:WOT 
 * 
 * @author WOT 
 * @version 2.0 
 */

package com.check.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 * 
 */
public class GenerateValidatePic {
    //为了避免识别0o,l 1,去掉
	private static final String CodesLists = "ABCDEFGHIJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz23456789";
 
	private HttpServletResponse response = null;
    private static final int Height = 20;
    private static final int Font_Num = 4;//生成的验证字符长度
    private int width = 0;
    private int iNum = 0;
    private String codeList = "";
    private boolean drawBgFlag = false;
    
    private int rBg = 0;
    private int gBg = 0;
    private int bBg = 0;
    
    public GenerateValidatePic(HttpServletResponse response) {
        this.response = response;
        this.width = 13 * Font_Num + 12;
        this.iNum = Font_Num;
        this.codeList = CodesLists;
    }
    
    public GenerateValidatePic(HttpServletResponse response,int iNum,String codeList) {
        this.response = response;
        this.width = 13 * iNum + 12;
        this.iNum = iNum;
        this.codeList = codeList;        
    }
    public String generateRandPic(){
        BufferedImage image = new BufferedImage(width, Height,
                BufferedImage.TYPE_INT_RGB);
        
        Graphics g = image.getGraphics();
        
        Random random = new Random();
        
        if ( drawBgFlag ){
            g.setColor(new Color(rBg,gBg,bBg));
            g.fillRect(0, 0, width, Height);
        }else{
            g.setColor(getRandColor(200, 250));
            g.fillRect(0, 0, width, Height);
            
            for (int i = 0; i < 155; i++) {
                g.setColor(getRandColor(140, 200));
                int x = random.nextInt(width);
                int y = random.nextInt(Height);
                int xl = random.nextInt(12);
                int yl = random.nextInt(12);
                g.drawLine(x, y, x + xl, y + yl);
            }
        }
        
        g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        
        String sRand="";
        for (int i=0;i<iNum;i++){
            int rand=random.nextInt(codeList.length());
            String strRand=codeList.substring(rand,rand+1);
            sRand+=strRand;
            g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
            g.drawString(strRand,13*i+6,16);
        }
        g.dispose();
        try{
            ImageIO.write(image, "JPEG", response.getOutputStream());
        }catch(IOException e){
            
        }
        image=null;
        return sRand;
    }
    public void setBgColor(int r,int g,int b){
        drawBgFlag = true;
        this.rBg = r;
        this.gBg = g;
        this.bBg = b;
    }
    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
	/**
	 * Administrator <br>
	 * 
	 * @see Description：
	 * @param args
	 *            void
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
