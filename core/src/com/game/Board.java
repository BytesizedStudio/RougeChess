
package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.framework.GameScreen;
import com.framework.GameState;

public class Board {

	private static final int WHITE = 0, BLACK = 1, WALL = 2, GRAY = 3, GATE = 4, EXITGATE = 5, SIDEWALL = 6, LEFTWALL = 7, RIGHTWALL = 8, WALLTOP = 9, WALLBOTTOM = 10;
    private final int xBuffer = 0;

	private static Board board;
	public static volatile boolean exit = false;
	public static boolean isFirstRoom = true;
	private Space[][] spaces;
	private GameState gameState;
	private BitmapFont font;
	
	public Board() {
		spaces = new Space[17][17];
		isFirstRoom = false;
		font = new BitmapFont();

		GameState.getInstance();
	}
	public Board(Space[][] room) {
		spaces = room;
		isFirstRoom = false;
	}
	
	public static Board getBoard() {
		if(Board.board == null)
			board = new Board();
		return board;
	}
	
	public void newBoard(Space[][] b) {
		board = new Board(b);
	}
	
    public Space[][] getSpaces() {
    	return spaces;
    }

    /*
	public void render(SpriteBatch batch, float delta) {
		for(int r = 0; r < spaces.length; r++) {
			for(int c = 0; c < spaces[r].length; c++) {
				batch.draw(spaces[r][c].getTexture(GRAY), c*32, r*32);
				if(spaces[r][c].getStatus() == Space.State.WALL)
					if(spaces[r][c].isEntrance())
						batch.draw(spaces[r][c].getTexture(4), c*32, r*32);
					else if(spaces[r][c].isExit())
						batch.draw(spaces[r][c].getTexture(5), c*32, r*32);
					else if(c > 0 && c < spaces[r].length-1 && spaces[r][c+1].getStatus() != Space.State.WALL && spaces[r][c-1].getStatus() != Space.State.WALL && r > 0 && r < spaces.length-1 && (spaces[r+1][c].getStatus() == Space.State.WALL || spaces[r-1][c].getStatus() == Space.State.WALL))
						batch.draw(spaces[r][c].getTexture(6), c*32, r*32);
					else if((c == 0  && spaces[r][c+1].getStatus() != Space.State.WALL) || (c == spaces[r].length-1 && spaces[r][c-1].getStatus() != Space.State.WALL) && r > 0 && r < spaces.length-1 && (spaces[r+1][c].getStatus() == Space.State.WALL || spaces[r-1][c].getStatus() == Space.State.WALL))
						batch.draw(spaces[r][c].getTexture(RIGHTWALL), c*32, r*32);
					else if((r < 16 && !spaces[r+1][c].isWall() && spaces[r+1][c].isClear())){
						batch.draw(spaces[r][c].getTexture(WALL), c * 32, r * 32);
						batch.draw(spaces[r][c].getTexture(WALLBOTTOM), c * 32, r * 32 + 32);
					} else if(r > 0 && !spaces[r-1][c].isWall()) {
						batch.draw(spaces[r][c].getTexture(WALLTOP), c * 32, r * 32);
					} else {}
				else if(spaces[r][c].getStatus() == Space.State.CLEAR)
					batch.draw(spaces[r][c].getTexture(BLACK), c*32, r*32);
						batch.draw(spaces[r][c].getTexture(6), c*32, r*32);
					else
						batch.draw(spaces[r][c].getTexture(2), c*32, r*32);
				else if(spaces[r][c].getStatus() == Space.State.CLEAR) {}
					//batch.draw(spaces[r][c].getTexture(3), c*32, r*32);
				else if(r % 2 == c % 2)
					batch.draw(spaces[r][c].getTexture(0), c*32, r*32);
				else
					batch.draw(spaces[r][c].getTexture(1), c*32, r*32);


			}
		}
	}
	*/

	public void render(SpriteBatch batch, float delta) {



		for(int r = 0; r < spaces.length; r++) {
			for(int c = 0; c < spaces[r].length; c++) {

				spaces[r][c].render(batch);
				if(font == null) {
				    font = new BitmapFont();
				    font.getData().setScale(0.3f);
                }
				font.draw(batch, "R: " + r + " C: " + c, c * 32, r * 32 + 4);

				/*
				if(spaces[r][c].isClear()) {
					batch.draw(spaces[r][c].getTexture(GRAY), c * 32, r * 32);
				}
				else if(spaces[r][c].isWall()) {


					if(spaces[r + 1][c].isClear() && !spaces[r-1][c].isClear()) {
						batch.draw(spaces[r][c].getTexture(WALL), c * 32, r * 32);
						batch.draw(spaces[r][c].getTexture(WALLBOTTOM), c * 32, (r + 1) * 32);
					} else if(!spaces[r + 1][c].isWall()) {
						batch.draw(spaces[r][c].getTexture(WALLTOP), c * 32, r * 32);
					} else {

					}


				}
				else {

					if(spaces[r][c].isExit()) {
						batch.draw(spaces[r][c].getTexture(EXITGATE), c * 32, r * 32);
					}
					else if(spaces[r][c].isEntrance()) {
						batch.draw(spaces[r][c].getTexture(GATE), c * 32, r * 32);
					}
					else if(r % 2 == c % 2) {
						batch.draw(spaces[r][c].getTexture(WHITE), c * 32, r * 32);
					}
					else {
						batch.draw(spaces[r][c].getTexture(BLACK), c * 32, r * 32);
					}

				}
				*/
			}

		}

	}

	public void renderAttack(ShapeRenderer shapeRenderer) {
		for(int r = 0; r < spaces.length; r++) {
			for(int c = 0; c < spaces[r].length; c++) {
				if(spaces[r][c].isAttacked()) {
					shapeRenderer.setProjectionMatrix(GameState.getInstance().getCamera().combined);
					shapeRenderer.setColor(new Color(1, 0, 0, 0.5f));
					shapeRenderer.rect(c * 32, r * 32, 32, 32);
				}
			}
		}
	}
	
	public void update(float delta) {
		for(int r = 0; r < spaces.length; r++)
			for(int c = 0; c < spaces[r].length; c++)
				spaces[r][c].update(delta);
	}
	
}

