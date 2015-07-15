/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gl3dgame.perspect;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES1;
import com.jogamp.opengl.GL2GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.fixedfunc.GLLightingFunc;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;

/**
 *
 * @欢迎大家光临子瑜IT博客空间.
 */
public class GLRender implements GLEventListener {

    float xRot = 0.0f;
    float yRot = 0.0f;
    //    GL gl;
    GL2 gl;
    GLU glu;

    @Override
    public void init(GLAutoDrawable drawable) {
        //        gl = drawable.getGL();
        gl = drawable.getGL().getGL2();
        glu = new GLU();

        float whiteLight[] = {0.45f, 0.45f, 0.45f, 1.0f};
        float sourceLight[] = {0.25f, 0.25f, 0.25f, 1.0f};
        float lightPos[] = {-50.f, 25.0f, 250.0f, 0.0f};

        gl.glEnable(GL.GL_DEPTH_TEST);	//启用深度测试 Hidden surface removal
        gl.glFrontFace(GL.GL_CCW);		//设置CCW方向为“正面”，CCW即CounterClockWise，逆时针
        gl.glEnable(GL.GL_CULL_FACE);	//用GL_CULL_FACE符号常量调用glEnable函数表示开启多边形表面剔除功能

        // Enable lighting  启用灯光
        gl.glEnable(GLLightingFunc.GL_LIGHTING);

        // Setup and enable light 0   设置灯光
        gl.glLightModelfv(GL2ES1.GL_LIGHT_MODEL_AMBIENT, whiteLight, 0);
        gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_AMBIENT, sourceLight, 0);
        gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_DIFFUSE, sourceLight, 0);
        gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_POSITION, lightPos, 0);
        gl.glEnable(GLLightingFunc.GL_LIGHT0);

        // Enable color tracking   启用材质
        gl.glEnable(GLLightingFunc.GL_COLOR_MATERIAL);

        // 决定对物体的正面还是反面，对环境光、镜面光还是漫射光进行颜色跟踪。
        //第一个参数可以取GL_FRONT、GL_BACK、GL_FRONT_AND_BACK中的任意一种，
        //第二个参数可以取GL_AMBIENT、GL_DIFFUSE、GL_AMBIENT_AND_DIFFUSE、GL_SPECULAR中的任意一种。
        gl.glColorMaterial(GL.GL_FRONT, GLLightingFunc.GL_AMBIENT_AND_DIFFUSE);

        // 黑色背景
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

    }

    @Override
    public void display(GLAutoDrawable drawable) {
        float fZ,bZ;

        // 填充背景为背景颜色
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        fZ = 100.0f;
        bZ = -100.0f;

        // 保存矩阵状态和做旋转
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.0f, -300.0f);
        gl.glRotatef(xRot, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(yRot, 0.0f, 1.0f, 0.0f);

        // 设置绘画颜色为红
        gl.glColor3f(1.0f, 0.0f, 0.0f);

        // Front Face ///////////////////////////////////
        gl.glBegin(GL2GL3.GL_QUADS);
        //直截了当地指着Z轴,也就是指向前面的法线
        gl.glNormal3f(0.0f, 0.0f, 1.0f);

        // 正面左横
        gl.glVertex3f(-50.0f, 50.0f, fZ);
        gl.glVertex3f(-50.0f, -50.0f, fZ);
        gl.glVertex3f(-35.0f, -50.0f, fZ);
        gl.glVertex3f(-35.0f,50.0f,fZ);

        //正面右横
        gl.glVertex3f(50.0f, 50.0f, fZ);
        gl.glVertex3f(35.0f, 50.0f, fZ);
        gl.glVertex3f(35.0f, -50.0f, fZ);
        gl.glVertex3f(50.0f,-50.0f,fZ);

        //正面上横
        gl.glVertex3f(-35.0f, 50.0f, fZ);
        gl.glVertex3f(-35.0f, 35.0f, fZ);
        gl.glVertex3f(35.0f, 35.0f, fZ);
        gl.glVertex3f(35.0f, 50.0f,fZ);

        //正面下横
        gl.glVertex3f(-35.0f, -35.0f, fZ);
        gl.glVertex3f(-35.0f, -50.0f, fZ);
        gl.glVertex3f(35.0f, -50.0f, fZ);
        gl. glVertex3f(35.0f, -35.0f,fZ);

        // 顶外 ////////////////////////////
        // Normal points up Y axis
        gl.glNormal3f(0.0f, 1.0f, 0.0f);
        gl.glVertex3f(-50.0f, 50.0f, fZ);
        gl.glVertex3f(50.0f, 50.0f, fZ);
        gl.glVertex3f(50.0f, 50.0f, bZ);
        gl.glVertex3f(-50.0f,50.0f,bZ);

        // 底外
        gl.glNormal3f(0.0f, -1.0f, 0.0f);
        gl.glVertex3f(-50.0f, -50.0f, fZ);
        gl.glVertex3f(-50.0f, -50.0f, bZ);
        gl.glVertex3f(50.0f, -50.0f, bZ);
        gl.glVertex3f(50.0f, -50.0f, fZ);

        // 左外
        gl.glNormal3f(1.0f, 0.0f, 0.0f);
        gl.glVertex3f(50.0f, 50.0f, fZ);
        gl.glVertex3f(50.0f, -50.0f, fZ);
        gl.glVertex3f(50.0f, -50.0f, bZ);
        gl.glVertex3f(50.0f, 50.0f, bZ);

        // 右外
        gl.glNormal3f(-1.0f, 0.0f, 0.0f);
        gl.glVertex3f(-50.0f, 50.0f, fZ);
        gl.glVertex3f(-50.0f, 50.0f, bZ);
        gl.glVertex3f(-50.0f, -50.0f, bZ);
        gl.glVertex3f(-50.0f, -50.0f, fZ);
        gl.glEnd();

        gl.glFrontFace(GL.GL_CW);		// 设置CW方向为“正面”，CW即ClockWise，顺时针

        gl.glBegin(GL2GL3.GL_QUADS);
        // Back section
        // Pointing straight out Z
        gl.glNormal3f(0.0f, 0.0f, -1.0f);

        // 后面左横
        gl.glVertex3f(-50.0f, 50.0f, bZ);
        gl.glVertex3f(-50.0f, -50.0f, bZ);
        gl.glVertex3f(-35.0f, -50.0f, bZ);
        gl. glVertex3f(-35.0f,50.0f,bZ);

        // 后面右横
        gl.glVertex3f(50.0f, 50.0f, bZ);
        gl.glVertex3f(35.0f, 50.0f, bZ);
        gl.glVertex3f(35.0f, -50.0f, bZ);
        gl. glVertex3f(50.0f,-50.0f,bZ);

        // 后面上横
        gl.glVertex3f(-35.0f, 50.0f, bZ);
        gl.glVertex3f(-35.0f, 35.0f, bZ);
        gl.glVertex3f(35.0f, 35.0f, bZ);
        gl. glVertex3f(35.0f, 50.0f,bZ);

        // 后面下横
        gl. glVertex3f(-35.0f, -35.0f, bZ);
        gl. glVertex3f(-35.0f, -50.0f, bZ);
        gl. glVertex3f(35.0f, -50.0f, bZ);
        gl. glVertex3f(35.0f, -35.0f,bZ);

        // Insides /////////////////////////////
        //灰色
        gl.glColor3f(0.75f, 0.75f, 0.75f);

        // 内上
        gl. glNormal3f(0.0f, 1.0f, 0.0f);
        gl. glVertex3f(-35.0f, 35.0f, fZ);
        gl. glVertex3f(35.0f, 35.0f, fZ);
        gl. glVertex3f(35.0f, 35.0f, bZ);
        gl. glVertex3f(-35.0f,35.0f,bZ);

        // 内下
        gl. glNormal3f(0.0f, 1.0f, 0.0f);
        gl. glVertex3f(-35.0f, -35.0f, fZ);
        gl. glVertex3f(-35.0f, -35.0f, bZ);
        gl. glVertex3f(35.0f, -35.0f, bZ);
        gl. glVertex3f(35.0f, -35.0f, fZ);

        // 内左
        gl. glNormal3f(1.0f, 0.0f, 0.0f);
        gl. glVertex3f(-35.0f, 35.0f, fZ);
        gl. glVertex3f(-35.0f, 35.0f, bZ);
        gl. glVertex3f(-35.0f, -35.0f, bZ);
        gl. glVertex3f(-35.0f, -35.0f, fZ);

        // 内右
        gl. glNormal3f(-1.0f, 0.0f, 0.0f);
        gl. glVertex3f(35.0f, 35.0f, fZ);
        gl.glVertex3f(35.0f, -35.0f, fZ);
        gl. glVertex3f(35.0f, -35.0f, bZ);
        gl. glVertex3f(35.0f, 35.0f, bZ);
        gl.glEnd();

        gl.glFrontFace(GL.GL_CCW);		//设置CCW方向为“正面”，CCW即CounterClockWise，逆时针

        // Restore the matrix state  还原状态矩阵
        gl.glPopMatrix();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        float fAspect;

        // 防止为零
        if (height == 0) {
            height = 1;
        }

        //视口设置为窗口尺寸
        gl.glViewport(0, 0, width, height);

        fAspect =(float) width / height;

        // Reset projection matrix stack
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glLoadIdentity();



        //透视投影 眼角度,比例,近可视,远可视
        glu.gluPerspective(53.0f, fAspect, 1.0, 400.0);

        // 重置模型观察矩阵堆栈
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    @Override
    public void dispose(GLAutoDrawable arg0)
    {
        // TODO Auto-generated method stub

    }
}
/////////////////////////////////////////////////////////////////////////////////
