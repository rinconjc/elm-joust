module Subscription exposing (..)

import Time exposing (Time,second)

import Model exposing (..)
import Model.Ui exposing (..)
import Window
import Task
import AnimationFrame
import Keyboard exposing (KeyCode)
import WebSocket

type Msg
  = ResizeWindow (Int,Int)
  | Tick Time
  | KeyChange Bool KeyCode --remote? up/down?
  | StartGame
  | TimeSecond Time
  | NoOp
  | WsMsg String


subscriptions : Model -> Sub Msg
subscriptions {ui} =
  let
      window = Window.resizes (\{width,height} -> ResizeWindow (width,height))
      keys = [ Keyboard.downs (KeyChange True)
             , Keyboard.ups (KeyChange False)
             ]
      animation = [ AnimationFrame.diffs Tick ]
      seconds = Time.every Time.second TimeSecond
  in
     (
     case ui.screen of
       StartScreen ->
         [ window, seconds ]

       PlayScreen ->
         [ window ] ++ keys ++ animation

       GameoverScreen ->
         [ window ] ++ keys

     ) ++ [WebSocket.listen wsServer WsMsg] |> Sub.batch


initialWindowSizeCommand : Cmd Msg
initialWindowSizeCommand =
  Task.perform (\{width,height} -> ResizeWindow (width,height)) Window.size
