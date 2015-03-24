/*
 *
 * Hands-On code of the book Introduction to Reliable Distributed Programming
 * by Christian Cachin, Rachid Guerraoui and Luis Rodrigues
 * Copyright (C) 2005-2011 Luis Rodrigues
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 *
 * Contact
 * 	Address:
 *		Rua Alves Redol 9, Office 605
 *		1000-029 Lisboa
 *		PORTUGAL
 * 	Email:
 * 		ler@ist.utl.pt
 * 	Web:
 *		http://homepages.gsd.inesc-id.pt/~ler/
 * 
 */

package irdp.protocols.tutorialDA.Atividade1;

import net.sf.appia.core.AppiaEventException;
import net.sf.appia.core.Direction;
import net.sf.appia.core.Event;
import net.sf.appia.core.Layer;
import net.sf.appia.core.Session;
import net.sf.appia.core.events.channel.ChannelInit;

/**
 * Session implementing the Bounded Print protocol.
 * <br>
 * After a defined number of print requests, an alarm
 * is sent and further request are denied.
 * 
 * @author alexp
 */
public class JobTransformationSession extends Session {
  
  /** Creates a new instance of BoundedPrintSession */
  public JobTransformationSession(Layer layer) {
    super(layer);
  }

  public void handle(Event event) {
    if (event instanceof ChannelInit) {
      handleChannelInit((ChannelInit) event);
    } else if (event instanceof SubmitEvent) {
      SubmitRequest((SubmitEvent) event);
    } else if (event instanceof ConfirmEvent) {
      ConfirmRequest((ConfirmEvent) event);
    }
  }

  private void handleChannelInit(ChannelInit init) {
    try {
      init.go();
    } catch (AppiaEventException e) {
      e.printStackTrace();
    }
  }

  private void SubmitRequest(SubmitEvent request) {
    
	  try 
      {
    	System.out.println("Submit recebido por transformation");
    	
    	//altera string para upper
    	String str = new String(request.getString());
    	request.setString(str.toUpperCase());
    	
        request.go();
      } 
      catch (AppiaEventException e) 
      {
    	  // em caso de problemas envia o erro para cima
    	  ErrorEvent alarm = new ErrorEvent();
          alarm.setChannel(request.getChannel());
          alarm.setDir(Direction.UP);
          alarm.setSourceSession(this);
          try 
          {
            alarm.init();
            
            System.out.println("Error enviado por transformation");
            
            alarm.go();
          } 
          catch (AppiaEventException e2) 
          {
            e2.printStackTrace();
          } 
      }
  }

  private void ConfirmRequest(ConfirmEvent conf) {
    
	  try {

	      ConfirmEvent ack = new ConfirmEvent();

	      System.out.println("Confirmacao recebida em transformation");

	      ack.setChannel(conf.getChannel());
	                                           
	      ack.setDir(Direction.UP); 
	      ack.setSourceSession(this);
	      ack.setId(conf.getId());
	      
	      ack.init();
	      
	      System.out.println("Enviando confirmacao para aplicacao");
	      ack.go(); 

	    } catch (AppiaEventException e) {
	      e.printStackTrace();
	    }
	  
  }
}
