
package de.rougemaster.dungeon;

import de.rougemaster.dungeon.character.enemyCharacter.EnemyCharacter;
import de.rougemaster.dungeon.character.playerCharacter.PlayableCharacter;
import de.rougemaster.dungeon.dungeon.Room;
import de.rougemaster.dungeon.dungeon.RoomCardinalDirection;
import de.rougemaster.dungeon.game.Game;
import de.rougemaster.dungeon.game.gameCommand.CharacterCommands.doNothingGameCommand;
import de.rougemaster.dungeon.game.gameCommand.CharacterCommands.moveGameCommand;
import de.rougemaster.dungeon.game.gameCommand.GameCommand;
import de.rougemaster.dungeon.game.gameCommand.playableCharacterCommands.attackUsingEquipmentCommand;
import de.rougemaster.dungeon.game.gameCommand.zombieCommands.zombieBiteAttackCommand;
import de.rougemaster.dungeon.game.gameCommand.zombieCommands.zombieClawAttackCommand;
import de.rougemaster.dungeon.lobby.LobbyCharType;


import java.util.Map;

class DungeonApplicationTests {


    /**
     * Test, um zu überprüfen, ob der Turnmanager einen gesendeten Gamecommand + Character
     * vom Game richtig interpretiert und ausführt
     */

    public static void testMoving(){
        Game game = new Game(10, 2);

        PlayableCharacter player = game.addPlayer();
        EnemyCharacter zombie = game.addEnemy(LobbyCharType.Zombie);
        System.out.println("Player Raum:" + player.getCurrentRoom());
        System.out.println("Zombie Raum:" + zombie.getCurrentRoom());

        Map<RoomCardinalDirection, Room> rooms = player.getCurrentRoom().getAdjacentRooms();
        Room roomToMoveTo = null;
        if (rooms.get(RoomCardinalDirection.East) != null) {
            roomToMoveTo = rooms.get(RoomCardinalDirection.East);
        } else if (rooms.get(RoomCardinalDirection.North) != null) {
            roomToMoveTo = rooms.get(RoomCardinalDirection.North);
        } else if (rooms.get(RoomCardinalDirection.South) != null) {
            roomToMoveTo = rooms.get(RoomCardinalDirection.South);
        } else if (rooms.get(RoomCardinalDirection.West) != null) {
            roomToMoveTo = rooms.get(RoomCardinalDirection.West);
        }

        GameCommand moveGameCommand = new moveGameCommand(player, roomToMoveTo);
        game.setCharacterTurn(moveGameCommand, player);
        game.getTurnManager().executeTurn();
        System.out.println("\nPlayer Raum nach Turn:" + player.getCurrentRoom());
        System.out.println("Zombie Raum: nach Turn" + zombie.getCurrentRoom());
    }

    public static void testFight(){
        Game game = new Game(10,1);
        PlayableCharacter player = game.addPlayer();
        EnemyCharacter zombie = game.addEnemy(LobbyCharType.Zombie);


		System.out.println("###########TEST START###########");
		System.out.println("Player Raum:" + player.getCurrentRoom());
		System.out.println("Character 2 Raum:" + zombie.getCurrentRoom());
		System.out.println("Fights of char1: " + game.getTurnManager().getFightManager().getFight(player)+ "\n\n\n");
		Room char2Room = zombie.getCurrentRoom();


		Map<RoomCardinalDirection, Room> rooms = zombie.getCurrentRoom().getAdjacentRooms();
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
		player.teleport(adjacentRoomToMoveTo);
		GameCommand moveGameCommand= new moveGameCommand(player, char2Room);
		GameCommand doNothingComman= new doNothingGameCommand(zombie);
		game.setCharacterTurn(moveGameCommand, player);
		game.setCharacterTurn(doNothingComman, zombie);

		game.getTurnManager().executeTurn();
		System.out.println("Player Raum nach Turn:"+player.getCurrentRoom());
		System.out.println("Zombie Raum: nach Turn"+zombie.getCurrentRoom());
		System.out.println("Fights of Player: " + game.getTurnManager().getFightManager().getFight(player));
		System.out.println("Fights of Zombie: " + game.getTurnManager().getFightManager().getFight(zombie) +"\n\n\n");
		System.out.println("START KAMPF !!!!!!!!!!");
		System.out.println("Zombie level: " + zombie.getDangerLevel());
		GameCommand attackUsingItem= new attackUsingEquipmentCommand();
		System.out.println("HP Zombie: "+ zombie.getHp());
		game.setCharacterTurn(attackUsingItem, player);
		game.setCharacterTurn(new doNothingGameCommand(zombie), zombie);
		game.getTurnManager().executeTurn();
		System.out.println("HP Zombie: "+ zombie.getHp());

		System.out.println("\n\nZWEITER ZUG !!!!!!!!!!");
		game.setCharacterTurn(new doNothingGameCommand(player), player);
		game.setCharacterTurn(new zombieClawAttackCommand(), zombie);

		System.out.println("HP Player: "+ player.getHp());
		game.getTurnManager().executeTurn();
		System.out.println("HP Player: "+ player.getHp());

    }

	public static void removeDeadCharacter(){
		Game game = new Game(10,1);
		PlayableCharacter player = game.addPlayer();
		EnemyCharacter zombie = game.addEnemy(LobbyCharType.Zombie);


		System.out.println("###########TEST START###########");
		System.out.println("Player Raum:" + player.getCurrentRoom());
		System.out.println("Character 2 Raum:" + zombie.getCurrentRoom());
		System.out.println("Fights of char1: " + game.getTurnManager().getFightManager().getFight(player)+ "\n\n\n");
		Room char2Room = zombie.getCurrentRoom();


		Map<RoomCardinalDirection, Room> rooms = zombie.getCurrentRoom().getAdjacentRooms();
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
		player.teleport(adjacentRoomToMoveTo);
		GameCommand moveGameCommand= new moveGameCommand(player, char2Room);
		GameCommand doNothingComman= new doNothingGameCommand(zombie);
		game.setCharacterTurn(moveGameCommand, player);
		game.setCharacterTurn(doNothingComman, zombie);
		game.getTurnManager().executeTurn();
		System.out.println("Player Raum nach Turn:"+player.getCurrentRoom());
		System.out.println("Zombie Raum: nach Turn"+zombie.getCurrentRoom());
		System.out.println("Fights of Player: " + game.getTurnManager().getFightManager().getFight(player));
		System.out.println("Fights of Zombie: " + game.getTurnManager().getFightManager().getFight(zombie) +"\n\n\n");
		System.out.println("START KAMPF !!!!!!!!!!");
		System.out.printf("Player HP before Fight:" + player.getHp());
		System.out.println("Zombie level: " + zombie.getDangerLevel());
		GameCommand zombieAttack= new zombieBiteAttackCommand();
		System.out.println("HP Zombie: "+ zombie.getHp());
		game.setCharacterTurn(zombieAttack, zombie);
		game.setCharacterTurn(new doNothingGameCommand(player), player);
		game.getTurnManager().executeTurn();
		game.setCharacterTurn(new doNothingGameCommand(player),player);
		System.out.println("Player HP after Attack:" + player.getHp());
		game.getTurnManager().executeTurn();
		if(game.getPlayerList().contains(player)){
			System.out.println("NOCH DA???");
		}else {
			System.out.println("ERFOLG !!!!");
		}



	}

	public static void testThreeCharactersInFight(){
		Game game = new Game(10,1);
		PlayableCharacter player = game.addPlayer();
		EnemyCharacter zombie = game.addEnemy(LobbyCharType.Zombie);
		PlayableCharacter player2= game.addPlayer();


		System.out.println("###########TEST START###########");
		System.out.println("Player Raum:" + player.getCurrentRoom());
		System.out.println("Zombie Raum:" + zombie.getCurrentRoom());
		System.out.println("Player2 Raum:" + zombie.getCurrentRoom());


		Room char2Room = zombie.getCurrentRoom();


		Map<RoomCardinalDirection, Room> rooms = zombie.getCurrentRoom().getAdjacentRooms();
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
		player.teleport(adjacentRoomToMoveTo);
		GameCommand moveGameCommand= new moveGameCommand(player, char2Room);
		GameCommand doNothingComman= new doNothingGameCommand(zombie);
		game.setCharacterTurn(moveGameCommand, player);
		game.setCharacterTurn(doNothingComman, zombie);
		game.setCharacterTurn(doNothingComman, player2);

		game.getTurnManager().executeTurn();
		System.out.println("TURN WIRD AUSGEFÜHRT"+player.getCurrentRoom());
		System.out.println("Player Raum nach Turn:"+player.getCurrentRoom());
		System.out.println("Zombie Raum nach Turn"+zombie.getCurrentRoom());
		System.out.println("Player 2 Raum nach Turn"+zombie.getCurrentRoom());
		System.out.println("----------------------------"+player.getCurrentRoom());
		System.out.println("Fights of Player: " + game.getTurnManager().getFightManager().getFight(player));
		System.out.println("Fights of Zombie: " + game.getTurnManager().getFightManager().getFight(zombie) +"\n\n\n");
		System.out.println("Fights of Player2: " + game.getTurnManager().getFightManager().getFight(player2) +"\n\n\n");

		GameCommand moveGameCommandForPlayer2= new moveGameCommand(player2, char2Room);
		GameCommand doNothingCommanPlayer= new doNothingGameCommand(player);
		game.setCharacterTurn(moveGameCommandForPlayer2, player2);
		game.getTurnManager().executeTurn();
		System.out.println("PLAYER 2 IST JETZT AUCH IM RAUM"+player.getCurrentRoom());
		System.out.println("Player 2 Raum nach Turn"+zombie.getCurrentRoom());
		game.getTurnManager().executeTurn();
		System.out.printf("FIGHT-LISTE");
		System.out.println("Fights of Player: " + game.getTurnManager().getFightManager().getFight(player));
		System.out.println("Fights of Zombie: " + game.getTurnManager().getFightManager().getFight(zombie) +"\n\n\n");
		System.out.println("Fights of Player2: " + game.getTurnManager().getFightManager().getFight(player2) +"\n\n\n");


	}
    public static void main(String[] args) {
        //testFight();
		//removeDeadCharacter();
		testThreeCharactersInFight();

    }
//
//
///**
//	 * Es wird getestet, ob der Turnmanager erfolgreich zwei spieler, die sich in einem Raum befinden,
//	 * an den Fightmanager zunächst nur übergibt
//	 */
//
//	@Test
//	void startFightTest(){
//		game.addPlayer(character1);
//		game.addPlayer(character2);
//
//		System.out.println("TEST START###########");
//		System.out.println("Character 1 Raum:" + character1.getCurrentRoom());
//		System.out.println("Character 2 Raum:" + character2.getCurrentRoom());
//		System.out.println("Fights of char1: " + game.getTurnManager().getFightManager().getFight(character1)+ "\n\n\n");
//		Room char2Room = character2.getCurrentRoom();
//
//
//		Map<RoomCardinalDirection, Room> rooms = character2.getCurrentRoom().getAdjacentRooms();
//		Room adjacentRoomToMoveTo = null;
//		if(rooms.get(RoomCardinalDirection.East)!=null){
//			adjacentRoomToMoveTo = rooms.get(RoomCardinalDirection.East);
//		}else if(rooms.get(RoomCardinalDirection.North)!=null){
//			adjacentRoomToMoveTo = rooms.get(RoomCardinalDirection.North);
//		}else if (rooms.get(RoomCardinalDirection.South)!=null){
//			adjacentRoomToMoveTo = rooms.get(RoomCardinalDirection.South);
//		}else if (rooms.get(RoomCardinalDirection.West)!=null){
//			adjacentRoomToMoveTo = rooms.get(RoomCardinalDirection.West);
//		}
//		character1.teleport(adjacentRoomToMoveTo);
//		GameCommand moveGameCommand= new moveGameCommand(character1, char2Room);
//		GameCommand doNothingComman= new doNothingGameCommand();
//		game.setCharacterTurn(doNothingComman, character2);
//		game.setCharacterTurn(moveGameCommand, character1);
//		game.getTurnManager().executeTurn();
//		System.out.println("Character 1 Raum nach Turn:"+character1.getCurrentRoom());
//		System.out.println("Character 2 Raum: nach Turn"+character2.getCurrentRoom());
//		System.out.println("Fights of char1: " + game.getTurnManager().getFightManager().getFight(character1));
//		System.out.println("Fights of char2: " + game.getTurnManager().getFightManager().getFight(character2) +"\n\n\n");
//		System.out.println("START KAMPF !!!!!!!!!!");
//		GameCommand attackUsingItem= new attackUsingEquipmentCommand();
//		System.out.println("HP Character 2: "+ character2.getHp());
//		game.setCharacterTurn(attackUsingItem, character1);
//		game.setCharacterTurn(new doNothingGameCommand(), character2);
//		game.getTurnManager().executeTurn();
//		System.out.println("HP Character2: "+ character2.getHp());
//	}
//
//
///**
//     * Character bewegt sich die ganze Zeit.
//     */
//
//    @Test
//    void Turnmanagerthread() {
//        game.addPlayer(character1);
//        game.addPlayer(character2);
//        TurnManager turnManager = game.getTurnManager();
//        FightManager fightManager = turnManager.getFightManager();
//        Random random = new Random();
//
//        //Start thread
//        TurnManagerThread turnManagerThread = new TurnManagerThread(game.getTurnManager());
//        Thread t1 = new Thread(turnManagerThread);
//        t1.start();
//        int timeCounter = 0;
//
//        while (true) {
//
//            //Random delay before action
//            int delay = random.nextInt(2000);
//            timeCounter += delay;
//            System.out.println("Pausing for " + delay + " milliseconds...");
//            try {
//                Thread.sleep(delay);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            if (timeCounter>=5000){
//                timeCounter = 0;
//                System.out.println("\nNEW TURN SHOULD EXECUTE NOW...\n");
//            }
//            //Character One move
//            Map<RoomCardinalDirection, Room> roomsForChar1 = character1.getCurrentRoom().getAdjacentRooms();
//            Room roomToMoveToForChar1 = null;
//            if (roomsForChar1.get(RoomCardinalDirection.East) != null) {
//                roomToMoveToForChar1 = roomsForChar1.get(RoomCardinalDirection.East);
//            } else if (roomsForChar1.get(RoomCardinalDirection.North) != null) {
//                roomToMoveToForChar1 = roomsForChar1.get(RoomCardinalDirection.North);
//            } else if (roomsForChar1.get(RoomCardinalDirection.South) != null) {
//                roomToMoveToForChar1 = roomsForChar1.get(RoomCardinalDirection.South);
//            } else if (roomsForChar1.get(RoomCardinalDirection.West) != null) {
//                roomToMoveToForChar1 = roomsForChar1.get(RoomCardinalDirection.West);
//            }
//
//            GameCommand moveGameCommand1 = new moveGameCommand(character1, roomToMoveToForChar1);
//            game.setCharacterTurn(moveGameCommand1, character1);
//
//            //Character Two move
//            Map<RoomCardinalDirection, Room> roomsForChar2 = character2.getCurrentRoom().getAdjacentRooms();
//            Room roomToMoveToForChar2 = null;
//            if (roomsForChar2.get(RoomCardinalDirection.East) != null) {
//                roomToMoveToForChar2 = roomsForChar2.get(RoomCardinalDirection.East);
//            } else if (roomsForChar2.get(RoomCardinalDirection.North) != null) {
//                roomToMoveToForChar2 = roomsForChar2.get(RoomCardinalDirection.North);
//            } else if (roomsForChar2.get(RoomCardinalDirection.South) != null) {
//                roomToMoveToForChar2 = roomsForChar2.get(RoomCardinalDirection.South);
//            } else if (roomsForChar2.get(RoomCardinalDirection.West) != null) {
//                roomToMoveToForChar2 = roomsForChar2.get(RoomCardinalDirection.West);
//            }
//
//
//            GameCommand moveGameCommand2 = new moveGameCommand(character2, roomToMoveToForChar2);
//            game.setCharacterTurn(moveGameCommand2, character2);
//
//
//            //Prints
//            System.out.println("\n\n\n-----CURRENT TURN-----");
//            System.out.println("ROOMS:");
//            System.out.println("Character ONE currently in Room: " + System.identityHashCode(character1.getCurrentRoom()));
//            System.out.println("Character TWO currently in Room: " + System.identityHashCode(character2.getCurrentRoom()));
//        }
//    }

}

