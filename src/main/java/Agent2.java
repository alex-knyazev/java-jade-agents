import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Agent2 extends Agent {
    private static final long serialVersionUID = 3663966406239393054L;

    public void setup() {
        addBehaviour(new CyclicBehaviour(this) {
            private static final long serialVersionUID = 1912882200351395625L;

            public void action() {
                ACLMessage msg = receive();
                try {
                    if (msg != null) {
                        System.out.println(" – " + myAgent.getLocalName() + " received: " + msg.getContentObject());

                        ArrayList<ArrayList<Integer>> matrix = (ArrayList<ArrayList<Integer>>) msg.getContentObject();

                        ArrayList<Integer> rowsSum = new ArrayList<>(Collections.nCopies(matrix.size(), 0));
                        ArrayList<Integer> colsSum = new ArrayList<>(Collections.nCopies(matrix.size(), 0));
                        for (int i = 0; i < matrix.size(); i++) {
                            for (int j = 0; j < matrix.get(i).size(); j++) {
                                rowsSum.set(i, rowsSum.get(i) + matrix.get(i).get(j));
                                colsSum.set(j, colsSum.get(j) + matrix.get(i).get(j));
                            }
                        }

                        System.out.printf("Rows sum: %s\n", Arrays.toString(rowsSum.toArray()));
                        System.out.printf("Cols sum: %s\n", Arrays.toString(colsSum.toArray()));

                        ACLMessage reply = msg.createReply();
                        reply.setPerformative(ACLMessage.INFORM);
                        reply.setContent("Pong");
                        send(reply);
                    }
                    block();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}