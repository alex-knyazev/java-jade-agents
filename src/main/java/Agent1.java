import jade.core.AID;
import jade.core.Agent;
import jade.core.AgentDescriptor;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.Random;

public class Agent1 extends Agent {
    private static int N = 3;
    private static final long serialVersionUID = 8257866411543354395L;

    public void setup() {
        System.out.println("Hello World, my name is : " + getAID().getName());
        addBehaviour(new CyclicBehaviour(this) {
            private static final long serialVersionUID = 7774831398907094833L;

            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    send();
                }
                block();
            }
        });
        send();
    }

    private void send() {
        AMSAgentDescription[] agents = null;
        try {
            SearchConstraints c = new SearchConstraints();
            c.setMaxResults(new Long(-1));
            agents = AMSService.search(this, new AMSAgentDescription(), c);
        } catch (Exception e) {
            System.out.println("Problem searching AMS:" + e);
            e.printStackTrace();
        }
        for (AMSAgentDescription agent : agents) {
            AID agentID = agent.getName();
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.addReceiver(agentID);
            msg.setLanguage("English");

            ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();

            for (int i = 0; i < N; i++) {
                ArrayList<Integer> row = new ArrayList<>();
                for (int j = 0; j < N; j++) {
                    row.add(new Random().nextInt(100) + 1);
                }
                matrix.add(row);
            }

            try {
                msg.setContentObject(matrix);
                send(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
