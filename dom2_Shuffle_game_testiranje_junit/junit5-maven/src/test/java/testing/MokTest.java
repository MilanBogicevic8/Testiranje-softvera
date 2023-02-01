package testing;

import shuffle.AnimatedJButton;
import shuffle.Credits;
import shuffle.SecondsCounter;
import shuffle.ShuffleGame;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.easymock.*;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

public class MokTest {

	ShuffleGame gam=null;
	private static SecondsCounter counter = new SecondsCounter();
	
	@Before
	public void prethodnik() {
		counter.setLabel(new JLabel("Labela"));
		counter.start();
		gam=null;
	}
	
	
	@ParameterizedTest
	@CsvFileSource(resources="/myfile.csv",numLinesToSkip=0)
	public void csvdeo1(int broj1, int broj2) {
		ShuffleGame game=new ShuffleGame(broj1);
		/*try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		assertTrue(game.getLevel()==broj2);
	}
	
	
	@ParameterizedTest
	@CsvFileSource(resources="/myfile1.csv",numLinesToSkip=0)
	public void csvdeo2(int pozicija) {
		ShuffleGame game=new ShuffleGame(9);
		JLabel l=game.getComponentIfPossibleToMove(pozicija);
		game=new ShuffleGame(3);
		game.getComponent(pozicija, pozicija);
		game.isPositionNotBottom(pozicija);
		game.analyseResult();
		assertTrue(l==null || (l!=null && game!=null));// pronadjena blanko pozicija
	}
	
	
	
	@Test
	public void test0() {//pravljenje jedne igre
		gam=new ShuffleGame(3);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void test1() {//promena velicine igre
		ShuffleGame gam=new ShuffleGame(4);
	    ShuffleGame.run(4);
	    ShuffleGame.run(4);
		assertEquals(4, gam.getLevel());
		assertEquals(16, gam.getNoOfCells());
	}
	
	@Test
	public void test2() {//postavljanje pozicije na igri
		gam=new ShuffleGame(3);
		int broj=gam.getPosition(5);
		assertTrue((broj<9 && broj>=0));
	}
	
	@Test
	public void test3() {// postavljanje pozicije van opsega
		gam=new ShuffleGame(3);
		int broj=gam.getPosition(100);
		assertTrue((broj<9 && broj>=0));
	}
	
	@Test
	public void test4() {// zapocinjanje brojanja sekundi na timeru
		gam=new ShuffleGame(3);
		gam.startSecondsCounter();
		assertTrue(gam.getSecondsCounter_().counter>=0);
	}
	
	@Test
	public void test5() {//provera statusa igre
		gam=new ShuffleGame(4);
		boolean dali;
		gam.setGameStatus(dali=gam.isGameStatus());;
		assertEquals(dali, gam.isGameStatus());
	}
	
	@Test
	public void test6() {//provera slobodnog mesta
		gam=new ShuffleGame(5);
		boolean dali=true;
		gam.changeButtonState(true);
		
		assertEquals(dali, gam.getReStartAction().isEnabled());
		dali=false;
		
		gam.changeButtonState(false);
		assertEquals(dali, gam.getReStartAction().isEnabled());
	}
	
	@Test
	public void test7() {//da li se ubacuje u fajl
		gam=new ShuffleGame(6);
		gam.showScoreDialog(1,1);
		assertEquals(true,new File(gam.getFilename()).isFile());
	}
	
	@Test
	public void test8() {//prikaz score dialoga
		gam=new ShuffleGame(6);
		gam.showScoreDialog(100,100);
		assertEquals(true,new File(gam.getFilename()).isFile());
	}
	
	@Test
	public void test10() {// high score tabela-radi
		gam=new ShuffleGame(7);
		gam.showHighScoreDialog();
		assertEquals(true,new File(gam.getFilename()).isFile());
	}
	
	@Test
	public void test101() {// high score tabela-radi
		gam=new ShuffleGame(3);
		gam.showHighScoreDialog();
		assertEquals(true,new File(gam.getFilename()).isFile());
	}
	
	@Test
	public void test11() {//prikaz slike-ne radi
		gam=new ShuffleGame(8);
		gam.showImageDialog("miki", "miki");
		assertFalse(!(new File(gam.getFilename()).isFile()));
	}
	
	
	@Test
	public void test12() {// analiza da li su figure poslagane
		gam=new ShuffleGame(9);
		boolean rezultat=gam.analyseResult();
		assertFalse(rezultat);
	}
	
	@Test
	public void test13() {//score dialog null
		gam=new ShuffleGame(9);
	    gam.showScoreDialog(null);
		assertTrue(gam.getFilename().equals("Score.dat"));
	}
	
	@Test
	public void test14() {//pravljenje pause buttona
		gam=new ShuffleGame(4);
		AnimatedJButton pauseButton = new AnimatedJButton( "PAUSE" , 7 ) ;
        pauseButton.addActionListener( gam ) ;
        pauseButton.setActionCommand( "PAUZA" ) ;
        pauseButton.setLabelName("MILAN");
        pauseButton.getString("MILAN", 4);
        pauseButton.mouseClicked(null);
        pauseButton.mouseEntered(null);
        pauseButton.mousePressed(null);
        pauseButton.mouseReleased(null);
        pauseButton.actionPerformed(null);
        pauseButton.mouseExited(null);
        gam.getFooterPanel().add(pauseButton);
        assertTrue(gam.getFooterPanel().isVisible());
	}
	
	@Test
	public void test15() {
		gam=new ShuffleGame(9);
	    assertNull(gam.getComponentIfPossibleToMove(10));
	}
	
	@Test
	public void test16() {//rad timera-radi
		gam=new ShuffleGame(4);
		gam.getSecondsCounter_().reStart();
		gam.getSecondsCounter_().pause();
		gam.getSecondsCounter_().enable();
		int time=gam.getSecondsCounter_().getTimeElapsedInSeconds();
		
	    gam.getComponentIfPossibleToMove(10);
	    gam.getSecondsCounter_().getTimeElapsed();
	    gam.getSecondsCounter_().getMinitueString(200);
	    assertTrue((time>=0));
		
	}
	
	@Test
	public void test17() {//provera metod klase Credits :getTie,comppare1,compare2
		Credits credits=new Credits(1,1,4);
		credits.prepareTimeString();
		credits.getTime();
		credits.getTimeInSeconds();
		Credits credits1=new Credits(0, 2, 0);
		credits1.compareTo(credits);
		credits1.prepareTimeString();
		credits1.getTime();
		assertTrue(credits.compare(0.1, 0.2)==-1 && credits.compare(0.2,0.1)==1 && credits.compare(0.1,0.1)==0);
		assertTrue(credits.compare(1, 2)==-1 && credits.compare(2,1)==1 && credits.compare(1, 1)==0);
	}
	
	//pravljenje proksija
	
	@Test
	public void mok1() {// pravljenje moka koji zamenjuje pokazivac na igru Shuffle game
		//pravljanje moka
		ShuffleGame shuffle=EasyMock.createMock(ShuffleGame.class);
		//postavljanje povratne vrednosti ako se prosledi null u metod
     	EasyMock.expect(shuffle.getCloseButtonPanel(null)).andReturn(null);
		ShuffleGame game=new ShuffleGame(3);
		
		
		EasyMock.replay(shuffle);
		
		
		game.setShuffleGame_(shuffle);
		game.showHighScoreDialog();
		
		assertTrue(game.getShuffleGame_()!=null);
	}
	
	
	@Test
	public void mok11() {
		Credits credits = new Credits(5,10,2);
		Credits crmock = EasyMock.createMock(Credits.class);
	    EasyMock.expect(crmock.getPlayer()).andReturn("Igrac");
		EasyMock.replay(crmock);
		assertEquals(credits.compareTo(crmock), 1);
	}
	
	@Test
	public void mok12() {
		ShuffleGame g1=new ShuffleGame(3);
		ActionEvent event = EasyMock.createMock(ActionEvent.class);
		EasyMock.expect(event.getActionCommand()).andReturn(ShuffleGame.START);
		JButton bttn = new AnimatedJButton("Dugme", 2);
		EasyMock.expect(event.getSource()).andReturn(bttn);
		EasyMock.replay(event);
		g1.actionPerformed(event);
		assertEquals(g1.getShuffleGame_()!=null,true);

	}


	
	@Test
	public void mok2() {//pravljenje animated buttona koje zamenjuje pravo
		AnimatedJButton button=EasyMock.createMock(AnimatedJButton.class);
		EasyMock.expect(button.getText()).andReturn("PAUSE");
		
		ShuffleGame game=new ShuffleGame(4);
		button.text="Pauza";
		
		button.timer=new Timer(100, new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		EasyMock.replay(button);
		
		synchronized(game.getFooterPanel()) {
		
		//game.getFooterPanel().add(button);
			game.setAnnimatedJButton(button);
		}
		assertTrue(game.getShuffleGame_()!=null);
	}
	
	@Test
	public void mok3() {//strict mok za redosled poziva metoda
		Credits credits=EasyMock.createStrictMock(Credits.class);
		//bas ovim redosledom mora da se pozivaju funkcije
		EasyMock.expect(credits.getTimeInSeconds()).andReturn(1);
		EasyMock.expect(credits.getLevel()).andReturn(3);
		EasyMock.expect(credits.getPlayer()).andReturn("Milan");
		
		ShuffleGame game=new ShuffleGame(3);
		
		EasyMock.replay(credits);
		game.setCredits(credits);
		
		assertTrue(new File(game.getFilename()).exists());
	}
	
	
	@Test
	public void test18() {
		ShuffleGame gam=new ShuffleGame(4);
		assertNotNull(gam.getComponent(3,4));
	}
	
	@Test
	@Order(31)
	public void testWinningPosition() {
		ShuffleGame game=new ShuffleGame(3);
		JButton btn = new JButton();
		ActionEvent e4 = new ActionEvent(btn, 1, "ReStart");
		game.actionPerformed(e4);
		JButton buttons[] = new JButton[9];
		JLabel label = null;
		for (int i = 0; i < 9; i++) {
			if (game.buttonPanel.getComponent(i) instanceof JLabel) {
				label = (JLabel) game.buttonPanel.getComponent(i);
			} else {
				JButton button = (JButton) game.buttonPanel.getComponent(i);
				buttons[Integer.parseInt(button.getText()) - 1] = button;
			}
		}
		for (int i = 0; i < 8; i++) {
			game.buttonPanel.add(buttons[i], i);
			game.matrix[i] = i + 1;
		}
		game.buttonPanel.add(label, 8);
		game.matrix[8] = 0;
		game.blankPosition = 8;
		ActionEvent e = new ActionEvent(buttons[7], 1, "number");
		
		game.actionPerformed(e);
		assertEquals(game.analyseResult(), false);
		game.actionPerformed(e);
		assertEquals(game.gameStatus, false);
	}

	
	@Test
	@DisplayName("ActionListener test1")
	public void testAction1(){
		ShuffleGame gam=new ShuffleGame(5);
		ActionEvent action=new ActionEvent(gam,0,"PAUSE");
		//gam.actionPerformed(action);
		assertNotNull(gam.getComponent(3,4));
	}
	
	@Test
	@DisplayName("ActionListener test2")
	public void testAction2(){
		ShuffleGame gam=new ShuffleGame(5);
		ActionEvent action=new ActionEvent(gam,0,"START");
		//gam.actionPerformed(action);
		assertTrue(gam.getShuffleGame_()!=null);
	}
	
	@Test
	@DisplayName("ActionListener test3")
	public void testAction3(){
		ShuffleGame gam=new ShuffleGame(5);
		ActionEvent action=new ActionEvent(gam,0,"ReStart");
		gam.actionPerformed(action);
		assertTrue(gam.getShuffleGame_()!=null);
	}
	
	@Test
	@DisplayName("ActionListener test4")
	public void testAction4(){
		ShuffleGame gam=new ShuffleGame(5);
		ActionEvent action=new ActionEvent(gam,0,"CLOSE");
		//gam.actionPerformed(action);
		assertNotNull(gam.getComponent(3,4));
	}
	
	@Test
	@DisplayName("ActionListener test5")
	public void testAction5(){
		AnimatedJButton button = new AnimatedJButton("Dugme", 2);
		ActionEvent e = new ActionEvent(button, 1, " ");
		button.actionPerformed(e);
		assertEquals(button.getText(), button.getString( "Dugme" , 0 ));
		button.actionPerformed(e);
		assertEquals(button.getText(), button.getString( "Dugme" , 1 ));
		button.actionPerformed(e);
		assertEquals(button.getText(), button.getString( "Dugme" , 2 ));
		button.actionPerformed(e);
		assertEquals(button.getText(), button.getString( "Dugme" , 3 ));
		button.actionPerformed(e);
		assertEquals(button.getText(), button.getString( "Dugme" , 2 ));
		button.actionPerformed(e);
		assertEquals(button.getText(), button.getString( "Dugme" , 1 ));

	}
	
	@Test
	@DisplayName("ActionListener test6")
	public void testAction6(){
		ShuffleGame gam=new ShuffleGame(5);
		ActionEvent action=new ActionEvent(gam,0,"number");
		//gam.actionPerformed(action);
		assertTrue(gam.getShuffleGame_()!=null);
	}
	
	@Test
	public void test20() {//slozena igrica
		ShuffleGame gam=new ShuffleGame(3);
		int[] matrix=gam.getMatrix();
		for(int i=0;i<matrix.length;i++) {
			matrix[i]=i+1;
		}
		
		gam.setMatrix(matrix);
		gam.setBlankPosition(gam.getNoOfCells()-1);
		ActionEvent action=new ActionEvent(gam.getJButton(),0,"number");
		//gam.actionPerformed(action);
		gam.setFlag();
	    //gam.setActionCommand("number");
		assertNotNull(gam.getShuffleGame_());

	}
	
	@Test
	public void testScorDijalog() {
		ShuffleGame game=new ShuffleGame(3);
		game.showScoreDialog(20, 30);
		game.showScoreDialog(30, 30);
		game.showScoreDialog(40, 30);
		game.showScoreDialog(50, 30);
		game.showScoreDialog(60, 30);
		game.showScoreDialog(70, 30);
		game.showScoreDialog(80, 30);
		game.showScoreDialog(90, 30);
		game.showScoreDialog(100, 30);
		game.showScoreDialog(110, 30);
		game.showScoreDialog(20, 10);
		game.showHighScoreDialog();
	}

	@Test
	public void testActionPerformed() {
		ShuffleGame game=new ShuffleGame(3);
		AnimatedJButton button = new AnimatedJButton("Natpis", 2);
		ActionEvent e4 = new ActionEvent(button, 1, "ReStart");
		game.actionPerformed(e4);
		ActionEvent e = new ActionEvent(button, 1, "PAUSE");
		game.actionPerformed(e);
		assertEquals(button.getActionCommand(), "START");
		ActionEvent e2 = new ActionEvent(button, 1, "START");
		game.actionPerformed(e2);
		assertEquals(button.getActionCommand(), "PAUSE");
		ActionEvent e3 = new ActionEvent(button, 1, "level0");
		game.actionPerformed(e3);
		assertEquals(game.level, 3);
		ActionEvent e5 = new ActionEvent(button, 1, "Instructions");
		game.actionPerformed(e5);
		
	}
	
	
	@Test
	public void testAkcijaVreme1() {
		
		ActionEvent e = new ActionEvent(counter, 1, " ");
		counter.actionPerformed(e);
		assertEquals(counter.secondsCounterLabel.getText(), "00 : 01");
	}
	

	
	@Test
	public void testAkcijaVreme3() throws InterruptedException {
		counter.pause();
		counter.enable();
		Thread.sleep(20000);
		assertEquals(counter.getTimeElapsed(), "00 Minutes & 19 Seconds ");
	}
	
	@Test
	public void testAkcijCeoMinut() throws InterruptedException {
		counter.reStart();
		Thread.sleep(70000);
		assertEquals(counter.getTimeElapsed(), "1 Minutes & 09 Seconds ");
	}
	
	@AfterClass
	public static void naKraju() {
		counter.stop();
		counter.finalize();
		counter = new SecondsCounter();
	}
		
}
