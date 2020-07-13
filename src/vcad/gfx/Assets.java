/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vcad.gfx;

import java.awt.image.BufferedImage;

/**
 *
 * @author hzb59
 */
public class Assets {
    /*
        Itt töltjük be egyszer az összes felhasználandó képet a játékhoz statikus képként, hogy mindenhonnan el tudjuk érni.
    */
    public static BufferedImage level1Bg,menuBg;
    public static BufferedImage Gunner,Sniper,Rocketeer,Freezer,Speedster,Reaper,Trickster,Burner,Grenader;
    public static BufferedImage Slasher,Runner,Brute,Swarmer,Swarm,fireParticle,iceParticle,confusionParticle;
    public static BufferedImage moneyUI,healthUI,level1btn,level2btn,level3btn,level4btn,level5btn,failScene,finishScene,towerCircle;
    public static BufferedImage[] waweStartBtn,mmStartBtn,mmExitBtn,mmSelectBtn,restartBtn,gunnerBtn,sniperBtn,rocketeerBtn,exitBtn,upgradeBtn,
                                  burnerBtn,grenaderBtn,freezerBtn,speedsterBtn,reaperBtn,tricksterBtn;
    
    
    public static void init(){
        level1Bg = ImageLoader.loadImgae("/backgrounds/level1.png");
        menuBg = ImageLoader.loadImgae("/backgrounds/menu.png");
        Gunner = ImageLoader.loadImgae("/VCAD/GunnerTower.png"); 
        Burner = ImageLoader.loadImgae("/VCAD/BurnerTower.png");
        Grenader = ImageLoader.loadImgae("/VCAD/GrenaderTower.png");
        Rocketeer = ImageLoader.loadImgae("/VCAD/RocketeerTower.png");
        Speedster = ImageLoader.loadImgae("/VCAD/SpeedsterTower.png");
        Freezer = ImageLoader.loadImgae("/VCAD/FreezerTower.png");
        Sniper = ImageLoader.loadImgae("/VCAD/SniperTower.png");
        Trickster = ImageLoader.loadImgae("/VCAD/TricksterTower.png");
        Reaper = ImageLoader.loadImgae("/VCAD/ReaperTower.png");
        Slasher = ImageLoader.loadImgae("/enemies/Slasher.png");
        Runner = ImageLoader.loadImgae("/enemies/Runner.png");
        Brute = ImageLoader.loadImgae("/enemies/Brute.png");
        Swarmer = ImageLoader.loadImgae("/enemies/Swarmer.png");
        Swarm = ImageLoader.loadImgae("/enemies/Swarm.png");
        moneyUI = ImageLoader.loadImgae("/uiobjects/moneyUI.png");
        healthUI = ImageLoader.loadImgae("/uiobjects/healthUI.png");
        level1btn = ImageLoader.loadImgae("/uiobjects/level1btn.png");
        level2btn = ImageLoader.loadImgae("/uiobjects/level2btn.png");
        level3btn = ImageLoader.loadImgae("/uiobjects/level3btn.png");
        level4btn = ImageLoader.loadImgae("/uiobjects/level4btn.png");
        level5btn = ImageLoader.loadImgae("/uiobjects/level5btn.png");
        failScene = ImageLoader.loadImgae("/uiobjects/fail.png");
        finishScene = ImageLoader.loadImgae("/uiobjects/finish.png");
        towerCircle = ImageLoader.loadImgae("/uiobjects/towerCircle.png");
        fireParticle = ImageLoader.loadImgae("/VCAD/fireParticle.png");
        iceParticle = ImageLoader.loadImgae(("/VCAD/iceParticle.png"));
        confusionParticle = ImageLoader.loadImgae(("/VCAD/confusionParticle.png"));
        
        mmStartBtn = new BufferedImage[2];
        mmStartBtn[0] = ImageLoader.loadImgae("/uiobjects/MainStart1.png");
        mmStartBtn[1] = ImageLoader.loadImgae("/uiobjects/MainStart2.png");
        mmExitBtn = new BufferedImage[2];
        mmExitBtn[0] = ImageLoader.loadImgae("/uiobjects/MainExit1.png");
        mmExitBtn[1] = ImageLoader.loadImgae("/uiobjects/MainExit2.png");
        mmSelectBtn = new BufferedImage[2];
        mmSelectBtn[0] = ImageLoader.loadImgae("/uiobjects/MainSelect1.png");
        mmSelectBtn[1] = ImageLoader.loadImgae("/uiobjects/MainSelect2.png");
        waweStartBtn = new BufferedImage[2];
        waweStartBtn[0] = ImageLoader.loadImgae("/uiobjects/waweStart1.png");
        waweStartBtn[1] = ImageLoader.loadImgae("/uiobjects/waweStart2.png");
        restartBtn = new BufferedImage[2];
        restartBtn[0] = ImageLoader.loadImgae("/uiobjects/restartbtn1.png");
        restartBtn[1] = ImageLoader.loadImgae("/uiobjects/restartbtn2.png");
        gunnerBtn = new BufferedImage[2];
        gunnerBtn[0] = ImageLoader.loadImgae("/uiobjects/gunnerBtn1.png");
        gunnerBtn[1] = ImageLoader.loadImgae("/uiobjects/gunnerBtn2.png");
        sniperBtn = new BufferedImage[2];
        sniperBtn[0] = ImageLoader.loadImgae("/uiobjects/sniperBtn1.png");
        sniperBtn[1] = ImageLoader.loadImgae("/uiobjects/sniperBtn2.png");
        rocketeerBtn = new BufferedImage[2];
        rocketeerBtn[0] = ImageLoader.loadImgae("/uiobjects/rocketeerBtn1.png");
        rocketeerBtn[1] = ImageLoader.loadImgae("/uiobjects/rocketeerBtn2.png");
        exitBtn = new BufferedImage[2];
        exitBtn[0] = ImageLoader.loadImgae("/uiobjects/exitBtn1.png");
        exitBtn[1] = ImageLoader.loadImgae("/uiobjects/exitBtn2.png");
        upgradeBtn = new BufferedImage[2];
        upgradeBtn[0] = ImageLoader.loadImgae("/uiobjects/upgradeBtn1.png");
        upgradeBtn[1] = ImageLoader.loadImgae("/uiobjects/upgradeBtn2.png");
        burnerBtn = new BufferedImage[2];
        burnerBtn[0] = ImageLoader.loadImgae("/uiobjects/BurnerBtn1.png");
        burnerBtn[1] = ImageLoader.loadImgae("/uiobjects/BurnerBtn2.png");
        grenaderBtn = new BufferedImage[2];
        grenaderBtn[0] = ImageLoader.loadImgae("/uiobjects/GrenaderBtn1.png");
        grenaderBtn[1] = ImageLoader.loadImgae("/uiobjects/GrenaderBtn2.png");
        freezerBtn = new BufferedImage[2];
        freezerBtn[0] = ImageLoader.loadImgae("/uiobjects/FreezerBtn1.png");
        freezerBtn[1] = ImageLoader.loadImgae("/uiobjects/FreezerBtn2.png");
        speedsterBtn = new BufferedImage[2];
        speedsterBtn[0] = ImageLoader.loadImgae("/uiobjects/SpeedsterBtn1.png");
        speedsterBtn[1] = ImageLoader.loadImgae("/uiobjects/SpeedsterBtn2.png");
        reaperBtn = new BufferedImage[2];
        reaperBtn[0] = ImageLoader.loadImgae("/uiobjects/ReaperBtn1.png");
        reaperBtn[1] = ImageLoader.loadImgae("/uiobjects/ReaperBtn2.png");
        tricksterBtn = new BufferedImage[2];
        tricksterBtn[0] = ImageLoader.loadImgae("/uiobjects/TricksterBtn1.png");
        tricksterBtn[1] = ImageLoader.loadImgae("/uiobjects/TricksterBtn2.png");
    }
  
}
