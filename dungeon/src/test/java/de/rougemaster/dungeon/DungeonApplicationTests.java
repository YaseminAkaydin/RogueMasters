package de.rougemaster.dungeon;

import de.rougemaster.dungeon.character.playerCharacter.PlayableCharacter;
import de.rougemaster.dungeon.dungeon.Room;
import de.rougemaster.dungeon.dungeon.RoomCardinalDirection;
import de.rougemaster.dungeon.game.Game;
import de.rougemaster.dungeon.game.TurnManager;
import de.rougemaster.dungeon.game.fight.FightManager;
import de.rougemaster.dungeon.game.gameCommand.CharacterCommands.doNothingGameCommand;
import de.rougemaster.dungeon.game.gameCommand.CharacterCommands.moveGameCommand;
import de.rougemaster.dungeon.game.gameCommand.GameCommand;
import de.rougemaster.dungeon.game.gameCommand.playableCharacterCommands.attackUsingEquipmentCommand;
import org.junit.jupiter.api.Test;

import java.util.Map;

class DungeonApplicationTests {
	Game game= new Game(10, 2);
	PlayableCharacter character1= new PlayableCharacter();
	PlayableCharacter character2= new PlayableCharacter();



	/**
	 * Test, um zu überprüfen, ob der Turnmanager einen gesendeten Gamecommand + Character
	 * vom Game richtig interpretiert und ausführt
	 */

	@Test
	void takeTurnTest(){
		game.addPlayer(character1);
		game.addPlayer(character2);
		System.out.println("Character 1 Raum:" + character1.getCurrentRoom());
		System.out.println("Character 2 Raum:" + character2.getCurrentRoom());

		Map<RoomCardinalDirection, Room> rooms = character1.getCurrentRoom().getAdjacentRooms();
		Room roomToMoveTo = null;
		if(rooms.get(RoomCardinalDirection.East)!=null){
			roomToMoveTo = rooms.get(RoomCardinalDirection.East);
		}else if(rooms.get(RoomCardinalDirection.North)!=null){
			roomToMoveTo = rooms.get(RoomCardinalDirection.North);
		}else if (rooms.get(RoomCardinalDirection.South)!=null){
			roomToMoveTo = rooms.get(RoomCardinalDirection.South);
		}else if (rooms.get(RoomCardinalDirection.West)!=null){
			roomToMoveTo = rooms.get(RoomCardinalDirection.West);
		}

		GameCommand moveGameCommand= new moveGameCommand(character1, roomToMoveTo);
		game.setCharacterTurn(moveGameCommand, character1);
		game.getTurnManager().executeTurn();
		System.out.println("Character 1 Raum nach Turn:"+character1.getCurrentRoom());
		System.out.println("Character 2 Raum: nach Turn"+character2.getCurrentRoom());

	}

	/**
	 * Es wird getestet, ob der Turnmanager erfolgreich zwei spieler, die sich in einem Raum befinden,
	 * an den Fightmanager zunächst nur übergibt
	 */
	@Test
	void startFightTest(){
		game.addPlayer(character1);
		game.addPlayer(character2);

		System.out.println("TEST START###########");
		System.out.println("Character 1 Raum:" + character1.getCurrentRoom());
		System.out.println("Character 2 Raum:" + character2.getCurrentRoom());
		System.out.println("Fights of char1: " + game.getTurnManager().getFightManager().getFight(character1)+ "\n\n\n");
		Room char2Room = character2.getCurrentRoom();


		Map<RoomCardinalDirection, Room> rooms = character2.getCurrentRoom().getAdjacentRooms();
		Room adjacentRoomToMoveTo = null;
		if(rooms.get(RoomCardinalDirection.East)!=null){
			adjacentRoomToMoveTo = rooms.get(RoomCardinalDirection.East);
		}else if(rooms.get(RoomCardinalDirection.North)!=null){
			adjacentRoomToMoveTo = rooms.get(RoomCardinalDirection.North);
		}else if (rooms.get(RoomCardinalDirection.South)!=null){
			adjacentRoomToMoveTo = rooms.get(RoomCardinalDirection.South);
		}else if (rooms.get(RoomCardinalDirection.West)!=null){
			adjacentRoomToMoveTo = rooms.get(RoomCardinalDirection.West);
		}
		character1.teleport(adjacentRoomToMoveTo);
		GameCommand moveGameCommand= new moveGameCommand(character1, char2Room);
		GameCommand doNothingComman= new doNothingGameCommand();
		game.setCharacterTurn(doNothingComman, character2);
		game.setCharacterTurn(moveGameCommand, character1);
		game.getTurnManager().executeTurn();
		System.out.println("Character 1 Raum nach Turn:"+character1.getCurrentRoom());
		System.out.println("Character 2 Raum: nach Turn"+character2.getCurrentRoom());
		System.out.println("Fights of char1: " + game.getTurnManager().getFightManager().getFight(character1));
		System.out.println("Fights of char2: " + game.getTurnManager().getFightManager().getFight(character2) +"\n\n\n");
		System.out.println("START KAMPF !!!!!!!!!!");
		GameCommand attackUsingItem= new attackUsingEquipmentCommand();
		System.out.println("HP Character 2: "+ character2.getHp());
		game.setCharacterTurn(attackUsingItem, character1);
		game.setCharacterTurn(new doNothingGameCommand(), character2);
		game.getTurnManager().executeTurn();
		System.out.println("HP Character2: "+ character2.getHp());
	}

	/**
	 * Es wird getestet, ob der Turnmanager alle Gamecomands von denjenigen Spielern richtig behandelt,
	 * die sich bereits in einem Kampf befinden
	 */
	@Test
	void handleCurrentFight(){


	}

}
