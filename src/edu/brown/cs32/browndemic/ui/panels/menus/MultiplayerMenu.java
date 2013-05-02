package edu.brown.cs32.browndemic.ui.panels.menus;

import edu.brown.cs32.browndemic.network.GameClient;
import edu.brown.cs32.browndemic.network.GameServer;
import edu.brown.cs32.browndemic.ui.Settings;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.brown.cs32.browndemic.ui.UIConstants.Colors;
import edu.brown.cs32.browndemic.ui.UIConstants.Fonts;
import edu.brown.cs32.browndemic.ui.UIConstants.Strings;
import edu.brown.cs32.browndemic.ui.UIConstants.UI;
import edu.brown.cs32.browndemic.ui.Utils;
import edu.brown.cs32.browndemic.ui.components.HoverLabel;
import edu.brown.cs32.browndemic.ui.panels.UIPanel;
import edu.brown.cs32.browndemic.ui.panels.titlebars.BackTitleBar;
import edu.brown.cs32.browndemic.world.ClientWorld;
import edu.brown.cs32.browndemic.world.ServerWorld;
import edu.brown.cs32.browndemic.world.WorldMaker;
import java.io.IOException;

public class MultiplayerMenu extends UIPanel implements MouseListener {

	private static final long serialVersionUID = 5817770674394990192L;
	
	JLabel _join, _create;
	JTextField _host;
	
	public MultiplayerMenu() {
		super();
		makeUI();
	}
	
	@Override
	protected void makeUI() {
		super.makeUI();

		JPanel host = new JPanel();
		host.setLayout(new BoxLayout(host, BoxLayout.X_AXIS));
		host.setBackground(Colors.TRANSPARENT);
		host.setMaximumSize(new Dimension(UI.WIDTH/2, 200));
		
		JLabel hostLabel = new JLabel(Strings.ENTER_HOST);
		hostLabel.setToolTipText(Strings.ENTER_HOST_TOOLTIP);
		hostLabel.setFont(Fonts.BIG_TEXT);
		hostLabel.setForeground(Colors.RED_TEXT);
		
		_host = new JTextField();
		_host.setToolTipText(Strings.ENTER_HOST_TOOLTIP);
		_host.setFont(Fonts.BIG_TEXT);
		_host.setForeground(Colors.RED_TEXT);
		_host.setBackground(Colors.MENU_BACKGROUND);
		
		host.add(Box.createGlue());
		host.add(hostLabel);
		host.add(_host);
		host.add(Box.createGlue());
		
		_join = new HoverLabel(Strings.JOIN_GAME, Fonts.BUTTON_TEXT, Colors.RED_TEXT, Colors.HOVER_TEXT);
		_join.setAlignmentX(CENTER_ALIGNMENT);
		_join.addMouseListener(this);
		_create = new HoverLabel(Strings.CREATE_GAME, Fonts.BUTTON_TEXT, Colors.RED_TEXT, Colors.HOVER_TEXT);
		_create.setAlignmentX(CENTER_ALIGNMENT);
		_create.addMouseListener(this);

		JLabel joinGame = new JLabel(Strings.CONNECT_TO_SERVER);
		joinGame.setFont(Fonts.BIG_TEXT);
		joinGame.setForeground(Colors.RED_TEXT);
		joinGame.setAlignmentX(CENTER_ALIGNMENT);
		
		add(Box.createGlue());
		//add(joinGame);
		add(host);
		add(_join);
		add(Box.createGlue());
		add(_create);
		add(Box.createGlue());
	}

	@Override
	public void setupForDisplay() {
		Utils.getParentFrame(this).setTitle(new BackTitleBar(new MainMenu()));
	}

	@Override
	public String toString() {
		return Strings.MULTIPLAYER_MENU;
	}
	
	@Override
	public void mouseReleasedInside(MouseEvent e) {
                int port = 6000;
//                int port = Integer.parseInt(Settings.get(Settings.PORT));
//                String name = Settings.get(Settings.NAME);
		if (e.getSource() == _join) {
			System.out.printf("Connect to %s\n", _host.getText());
                        
                        try{
                            int d = (int)(Math.random() * 100);
                            ClientWorld world = new ClientWorld("Client: " + d);
                            GameClient cli = new GameClient(_host.getText(), port, world);
                            Utils.getParentFrame(this).setPanel(new MultiplayerLobby(false, world, null));
                        }
                        catch(IOException ex){
                            //TODO: set message saying can't join
                        }
			//Utils.getParentFrame(this).setPanel(new MultiplayerJoinServer());
		} else if (e.getSource() == _create) {
                    try{
                        ServerWorld sw = WorldMaker.makeNewEarthServer();
                        GameServer server = new GameServer(sw, port);
                        ClientWorld world = new ClientWorld("Server");
                        GameClient cli = new GameClient("localhost", port, world);
			Utils.getParentFrame(this).setPanel(new MultiplayerLobby(true, world, sw));
			//Utils.getParentFrame(this).setPanel(new MultiplayerCreateServer());
                    }
                    catch(IOException ex){
                        //TODO: set message saying can't make server
                    }
		}
	}

}
