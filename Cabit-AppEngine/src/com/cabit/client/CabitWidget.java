/*
 * Copyright 2011 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.cabit.client;


import com.cabit.shared.CabitRequest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.TextAreaElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;



public class CabitWidget extends Composite {

  private static final int STATUS_DELAY = 4000;
  private static final String STATUS_ERROR = "status error";
  private static final String STATUS_NONE = "status none";
  private static final String STATUS_SUCCESS = "status success";

  interface CabitUiBinder extends UiBinder<Widget, CabitWidget> {
  }

  private static CabitUiBinder uiBinder = GWT.create(CabitUiBinder.class);

  @UiField
  TextAreaElement messageArea;

  @UiField
  InputElement recipientArea;

  @UiField
  DivElement status;

  @UiField
  Button sayHelloButton;

  @UiField
  Button sendMessageButton;

  /**
   * Timer to clear the UI.
   */
  Timer timer = new Timer() {
    @Override
    public void run() {
      status.setInnerText("");
      status.setClassName(STATUS_NONE);
      recipientArea.setValue("");
      messageArea.setValue("");
    }
  };

  private void setStatus(String message, boolean error) {
    status.setInnerText(message);
    if (error) {
      status.setClassName(STATUS_ERROR);
    } else {
      if (message.length() == 0) {
        status.setClassName(STATUS_NONE);
      } else {
        status.setClassName(STATUS_SUCCESS);
      }
    }

    timer.schedule(STATUS_DELAY);
  }

  public CabitWidget() {
    initWidget(uiBinder.createAndBindUi(this));
    sayHelloButton.getElement().setClassName("send centerbtn");
    sendMessageButton.getElement().setClassName("send");

    final EventBus eventBus = new SimpleEventBus();
    final MyRequestFactory requestFactory = GWT.create(MyRequestFactory.class);
    requestFactory.initialize(eventBus);

    sendMessageButton.addClickHandler(new ClickHandler() {
    public void onClick(ClickEvent event) {
    	final int add=1000;
        String recipient = recipientArea.getValue();
        String message = messageArea.getValue();
        setStatus("Connecting...", false);
        sendMessageButton.setEnabled(false);

        // Send a message using RequestFactory
        final CabitRequest request = requestFactory.cabitRequest();
        /*LocationProxy messageProxy = request.create(LocationProxy.class);
        messageProxy.setRecipient(recipient);
        messageProxy.setMessage(message);*/
        
        //Request<LocationProxy> sendRequest = request.readLocation("www.udi@gmail.com");
        /*sendRequest.fire(new Receiver<LocationProxy>() {
          @Override
          public void onFailure(ServerFailure error) {
            sendMessageButton.setEnabled(true);
            setStatus(error.getMessage(), true);
          }


		@Override
		public void onSuccess(LocationProxy response) {
			
			response.setLatitude(response.getLatitude()+add);
			response.setLongitude(response.getLongitude()+add);
			
			//request.updateLocation(response);
			
		}
        });*/
      }
    });

    sayHelloButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        /*sayHelloButton.setEnabled(false);
        HelloWorldRequest helloWorldRequest = requestFactory.helloWorldRequest();
        helloWorldRequest.getMessage().fire(new Receiver<String>() {
          @Override
          public void onFailure(ServerFailure error) {
            sayHelloButton.setEnabled(true);
            setStatus(error.getMessage(), true);
          }

          @Override
          public void onSuccess(String response) {
            sayHelloButton.setEnabled(true);
            setStatus(response, response.startsWith("Failure:"));
          }
        });*/
      }
    });
  }
}
