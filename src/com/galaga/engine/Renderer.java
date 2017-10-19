package com.galaga.engine;

import com.galaga.engine.gfx.Image;
import com.galaga.engine.gfx.ImageTile;

import java.awt.image.DataBufferInt;

public class Renderer
{
    private int pW, pH;
    private int[] p;

    public Renderer(GameContainer gc)
    {
        pW = gc.getWidth();
        pH = gc.getHeight();
        p = ((DataBufferInt)gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
    }

    public void clear()
    {
        for (int i = 0; i < p.length; i++)
        {
            p[i] = 0xFF000000;
        }
    }

    public void setPixel(int x ,int y ,int value)
    {
        if (x < 0 || x >= pW || y < 0 || y >= pH || value == 0xffff00ff){
            return;
        }

        p[x + y * pW] = value;
    }

    public void drawImage(Image image, int offX, int offY)
    {
        int newX = 0;
        int newY = 0;
        int newWidth = image.getW();
        int newHeight = image.getH();

        if (offX < -newWidth ) return;
        if (offY < -newHeight ) return;
        if (offX >= pW) return;
        if (offY >= pH) return;

        if (offX  < 0){ newX -= offX; }
        if (offY < 0){ newY -= offY; }
        if (newWidth + offX > pW){ newWidth -= (newWidth + offX - pW); }
        if (newHeight + offY > pH){ newHeight -= (newHeight + offY - pH); }

        for (int y = newY; y < newHeight; y++){
            for (int x = newX; x < newWidth; x++){
                setPixel(x + offX,y + offY, image.getP()[x + y * image.getW()]);
            }
        }
    }

    public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY)
    {
        int newX = 0;
        int newY = 0;
        int newWidth = image.getTileW();
        int newHeight = image.getTileH();

        if (offX < -newWidth ) return;
        if (offY < -newHeight ) return;
        if (offX >= pW) return;
        if (offY >= pH) return;

        if (offX  < 0){ newX -= offX; }
        if (offY < 0){ newY -= offY; }
        if (newWidth + offX > pW){ newWidth -= (newWidth + offX - pW); }
        if (newHeight + offY > pH){ newHeight -= (newHeight + offY - pH); }

        for (int y = newY; y < newHeight; y++){
            for (int x = newX; x < newWidth; x++){
                setPixel(x + offX,y + offY, image.getP()[(x + tileX * image.getTileW()) + (y + tileY * image.getTileH()) * image.getW()]);
            }
        }
    }
}