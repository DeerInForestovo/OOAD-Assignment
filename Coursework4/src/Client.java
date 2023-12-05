import java.util.InputMismatchException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        AirConditioner roomAirConditioner = new AirConditioner("bedRoom");
        AirConditioner livingAirConditioner = new AirConditioner("livingRoom");
        Light roomLight = new Light("bedRoom");
        Light livingLight = new Light("livingRoom");
        Command[] commands = new Command[9];
        commands[1] = new AirConditionerOnCommand(roomAirConditioner);
        commands[2] = new AirConditionerOffCommand(roomAirConditioner);
        commands[3] = new AirConditionerOnCommand(livingAirConditioner);
        commands[4] = new AirConditionerOffCommand(livingAirConditioner);
        commands[5] = new LightOnCommand(roomLight);
        commands[6] = new LightOffCommand(roomLight);
        commands[7] = new LightOnCommand(livingLight);
        commands[8] = new LightOffCommand(livingLight);
        RemoteCommandQueue remoteCommandQueue = new RemoteCommandQueue();
        Scanner input = new Scanner(System.in);
        System.out.println("""
                Please input operation number: 1-9:
                [1, 3, 5, 7] is on command, [2, 4, 6, 8] is off command,
                9 is to show state, 10 is to do, 11 is to undo, terminate by 0:""");
        /* test:
        1 3 5 7
        10
        10
        10
        10
        9
        11
        9
        2 4 6
        10
        10
        10
        9
        0

         */

        int op = 0;
        do {
            try {
                op = input.nextInt();
                switch (op) {
                    case 9:
                        System.out.println("Show state:");
                        showState(
                                new AirConditioner[] {roomAirConditioner, livingAirConditioner},
                                new Light[] {roomLight, livingLight}
                        );
                        System.out.println();
                        break;
                    case 10:
                        remoteCommandQueue.commandExecute();
                        break;
                    case 11:
                        remoteCommandQueue.commandUndo();
                        break;
                    default:
                        if (1 <= op && op <= 8) {
                            remoteCommandQueue.buttonPressed(commands[op]);
                        } else if (op != 0) {
                            System.out.println("Unknown command");
                        }
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Exception:" + e);
                input.nextLine();
            }
        } while (op != 0);
        input.close();
    }

    public static void showState(AirConditioner[] airConditioners, Light[] lights) {
        for (AirConditioner a: airConditioners) {
            System.out.println(a);
        }
        for (Light l: lights) {
            System.out.println(l);
        }
    }
}

