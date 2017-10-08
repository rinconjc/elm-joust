module Model exposing (..)

import Time exposing (Time)
import Set

import Model.Ui exposing (..)
import Model.Scene exposing (..)


type alias Model =
  { ui : Ui
  , scene : Scene
  , secondsPassed : Int }


initialModel : Model
initialModel =
  { ui = initialUi
  , scene = initialScene
  , secondsPassed = 0 }


freshGame : Ui -> Model
freshGame ui =
  let
      ui_ = { ui | screen = PlayScreen
                 , pressedKeys = Set.empty }
  in
      { initialModel | ui = ui_ }


winScore : Int
winScore =
  5

wsServer : String
wsServer = "ws://192.168.0.111:8080/"
