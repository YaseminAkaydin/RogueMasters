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
     * Character bewegt sich die ganze Zeit.
     */
    @Test
    void Turnmanagerthread() {
        game.addPlayer(character1);
        game.addPlayer(character2);
        TurnManager turnManager = game.getTurnManager();
        FightManager fightManager = turnManager.getFightManager();
        Random random = new Random();

        //Start thread
        TurnManagerThread turnManagerThread = new TurnManagerThread(game.getTurnManager());
        Thread t1 = new Thread(turnManagerThread);
        t1.start();
        int timeCounter = 0;

        while (true) {

            //Random delay before action
            int delay = random.nextInt(2000);
            timeCounter += delay;
            System.out.println("Pausing for " + delay + " milliseconds...");
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (timeCounter>=5000){
                timeCounter = 0;
                System.out.println("\nNEW TURN SHOULD EXECUTE NOW...\n");
            }
            //Character One move
            Map<RoomCardinalDirection, Room> roomsForChar1 = character1.getCurrentRoom().getAdjacentRooms();
            Room roomToMoveToForChar1 = null;
            if (roomsForChar1.get(RoomCardinalDirection.East) != null) {
                roomToMoveToForChar1 = roomsForChar1.get(RoomCardinalDirection.East);
            } else if (roomsForChar1.get(RoomCardinalDirection.North) != null) {
                roomToMoveToForChar1 = roomsForChar1.get(RoomCardinalDirection.North);
            } else if (roomsForChar1.get(RoomCardinalDirection.South) != null) {
                roomToMoveToForChar1 = roomsForChar1.get(RoomCardinalDirection.South);
            } else if (roomsForChar1.get(RoomCardinalDirection.West) != null) {
                roomToMoveToForChar1 = roomsForChar1.get(RoomCardinalDirection.West);
            }

            GameCommand moveGameCommand1 = new moveGameCommand(character1, roomToMoveToForChar1);
            game.setCharacterTurn(moveGameCommand1, character1);

            //Character Two move
            Map<RoomCardinalDirection, Room> roomsForChar2 = character2.getCurrentRoom().getAdjacentRooms();
            Room roomToMoveToForChar2 = null;
            if (roomsForChar2.get(RoomCardinalDirection.East) != null) {
                roomToMoveToForChar2 = roomsForChar2.get(RoomCardinalDirection.East);
            } else if (roomsForChar2.get(RoomCardinalDirection.North) != null) {
                roomToMoveToForChar2 = roomsForChar2.get(RoomCardinalDirection.North);
            } else if (roomsForChar2.get(RoomCardinalDirection.South) != null) {
                roomToMoveToForChar2 = roomsForChar2.get(RoomCardinalDirection.South);
            } else if (roomsForChar2.get(RoomCardinalDirection.West) != null) {
                roomToMoveToForChar2 = roomsForChar2.get(RoomCardinalDirection.West);
            }


            GameCommand moveGameCommand2 = new moveGameCommand(character2, roomToMoveToForChar2);
            game.setCharacterTurn(moveGameCommand2, character2);


            //Prints
            System.out.println("\n\n\n-----CURRENT TURN-----");
            System.out.println("ROOMS:");
            System.out.println("Character ONE currently in Room: " + System.identityHashCode(character1.getCurrentRoom()));
            System.out.println("Character TWO currently in Room: " + System.identityHashCode(character2.getCurrentRoom()));
        }
    }

}
