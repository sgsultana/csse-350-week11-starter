import java.util.Scanner;
import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.stage.Stage;



//    This program demonstrates the graphical display of a queue.

public class queueDemo extends Application
{
    public static void main(String [] args)
    {
       launch();
    }
    
    @Override
    public void start(Stage stage) throws Exception
    {
        // Create a queue.
        ArrayQueue queue = new ArrayQueue(4);
      
        // Create the GUI to display the queue.
        TextField[] qViewTextField = new TextField[4];
        TextField commandEntryTextField = new TextField();

        // Put the queue view text fields in a horizontal box.        
        HBox topHBox = new HBox(10);
        
        for (int k = 0; k < qViewTextField.length; k++)
        {
            TextField tF = new TextField();
            tF.setPrefColumnCount(5);
            tF.setEditable(false);
            topHBox.getChildren().add(tF);
            qViewTextField[k] = tF;
        }

        // Put the label and commandEntryTextField in a horizontal box.
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(new Label("Command"), commandEntryTextField);

        // Use an outer VBox to hold all the GUI components
        VBox outerPane = new VBox(10, topHBox, hBox);
        outerPane.setPadding(new Insets(10));  
        
        // Set the event handler on the text field
        EventHandler<ActionEvent> handler = new CommandTextHandler(queue, qViewTextField);
        commandEntryTextField.setOnAction(handler);

        stage.setScene(new Scene(outerPane));
        stage.setTitle("Queue Demo");
        stage.show();
    }
}

/*
   The handler for the command entry text field uses constructor parameters to store reference to the queue and the queue view text fields.
*/
class commandTextHandler implements EventHandler<ActionEvent>
{
    private ArrayQueue queue;
    private TextField[] qViewTextField;

    public commandTextHandler(ArrayQueue queue1, TextField[] qViewTfs)
    {
        queue = queue1;
        qViewTextField = qViewTfs;
    }

    public void handle(ActionEvent evt)
    {
        TextField commandTextField = (TextField) evt.getTarget();
        String commandText = commandTextField.getText();
        Scanner sc = new Scanner(commandText);
        if ( !sc.hasNext() )
        {
            return;
        }
        String command = sc.next();
        if ( command.equals("add") || command.equals("enqueue") )
        {
            String item = sc.next();
            queue.enqueue(item);
            refresh(queue.toString());
            return;
        }
        if ( command.equals("remove") || command.equals("dequeue") )
        {
            queue.dequeue();
            refresh(queue.toString());
            return;
        }
    }

    /*
       The refresh method stores the current queue entries into the corresponding text fields of the queue view.      
       @param qStr encodes the current contents of the queue.
     */
	 
    private void refresh(String qStr)
    {
        Scanner sc = new Scanner(qStr);
        sc.nextLine();    // Skip first, rear info      
        while (sc.hasNext())
        {
            int k = sc.nextInt();
            String qEntry = sc.next();
            qViewTextField[k].setText(qEntry);
        }
    }
}
