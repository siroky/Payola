package cz.payola.graphDrawer

import graphStructure.Vertex

/**
 * Created by IntelliJ IDEA.
 * User: Ondrej Kudlacek
 * Date: 12/14/11
 * Time: 8:09 PM
 */

class Main {

  import util.Constants._;
  import Drawer._;


  var select_rect = false;
  var select_rectOriginX = 0;
  var select_rectOriginY = 0;
  var select_VsLastTime = List[Vertex]();
  var select_touchedSelected = false;
  var select_Vs = 0;

  var mouse_useToleration = true;
  var mouse_moveToleration = 3; //count of pixels that are tolerated as "no mouse move", used for deselecting all vertices
  var mouse_buttonDownMove = false;
  var mouse_buttonDown = false;
  var mouse_x = 0;
  var mouse_y = 0;

  var keyboard_shiftDown = false;

  var canvas_width = 700; //TODO is variable
  var canvas_height = 700; //TODO is variable


  var ctx: Context = null;
  var document: Document = null;

  var graph: List[Vertex] = List[Vertex]();

  def main(args: Array[String]) {

    initGraph;

    document.onmousedown = MOUSE_CLICKED(null);
    document.onmouseup = MOUSE_RELEASED(null);
    document.onmousemove = MOUSE_MOVED(null);

    document.onkeydown = KEY_DOWN(null);
    document.onkeyup = KEY_UP(null);
  }
  
  private def initGraph {
    var v0 = new Vertex(0,  15,  15,  "0", null);
    var v1 = new Vertex(1,  120, 40,  "1", null);
    var v2 = new Vertex(2,  50,  120, "2", null);
    var v3 = new Vertex(3,  180, 60,  "3", null);
    var v4 = new Vertex(4,  240, 110, "4", null);
    var v5 = new Vertex(5,  160, 160, "5", null);
    var v6 = new Vertex(6,  240, 240, "6", null);
    var v7 = new Vertex(7,  270, 320, "7", null);
    var v8 = new Vertex(8,  160, 240, "8", null);
    var v9 = new Vertex(9,  120, 400, "9", null);
    var v10 = new Vertex(10, 300, 80,  "10", null);
    var v11 = new Vertex(11, 320, 30,  "11", null);
    var v12 = new Vertex(12, 300, 200, "12", null);
    var v13 = new Vertex(13, 350, 210, "13", null);
    var v14 = new Vertex(14, 300, 400, "14", null);
    var v15 = new Vertex(15, 80,  310, "15", null);
    var v16 = new Vertex(16, 15,  240, "16", null);
    var v17 = new Vertex(17, 15,  300, "17", null);
    var v18 = new Vertex(18, 400, 15,  "18", null);
    var v19 = new Vertex(19, 400, 120, "19", null);
    
    v0.neighbours = List[Vertex](v1, v2, v9, v11, v16);
    v1.neighbours = List[Vertex](v0, v5, v6);
    v2.neighbours = List[Vertex](v0, v3, v5, v6, v8);
    v3.neighbours = List[Vertex](v2, v4, v5, v11);
    v4.neighbours = List[Vertex](v3, v7, v8, v11);
    v5.neighbours = List[Vertex](v1, v2, v3, v6, v12);
    v6.neighbours = List[Vertex](v1, v2, v5, v7, v9);
    v7.neighbours = List[Vertex](v4, v6, v9);
    v8.neighbours = List[Vertex](v2, v4, v9, v16);
    v9.neighbours = List[Vertex](v0, v6, v7, v8, v10, v13, v15);
    v10.neighbours = List[Vertex](v9, v11, v12);
    v11.neighbours = List[Vertex](v0, v3, v4, v10, v13, v18, v19);
    v12.neighbours = List[Vertex](v5, v10, v13);
    v13.neighbours = List[Vertex](v9, v11, v12, v14, v19);
    v14.neighbours = List[Vertex](v13);
    v15.neighbours = List[Vertex](v9, v16, v17);
    v16.neighbours = List[Vertex](v0, v8, v15, v17);
    v17.neighbours = List[Vertex](v15, v16);
    v18.neighbours = List[Vertex](v11);
    v19.neighbours = List[Vertex](v11, v13);

    
    graph = List[Vertex](v0, v1, v2, v3, v4, v5, v6, v7, v8, v9, v10,
      v11, v12, v13, v14, v15, v16, v17, v18, v19);
  }

  /////////////////////////////////////////////////////////////////////////////
  //vertex processing
  /////////////////////////////////////////////////////////////////////////////

  //boolean coordinates function
  def isPointInRect(pointX: Int, pointY: Int,
                    rectLeft: Int, rectUp: Int, rectRight: Int, rectDown: Int): Boolean ={

    (pointX >= rectLeft) && (pointX <= rectRight) &&
      (pointY >= rectUp) && (pointY <= rectDown)
  }

  def deselectAll(graph: List[Vertex]): Boolean = {

    var smthChanged = false;

    if(select_Vs > 0){
      graph.foreach((vertex: Vertex) =>
        if(vertex.selected) {
          deselectVertex(vertex);
          smthChanged = true;
        }
      );
    }

    smthChanged;
  }

  //changes state of the input vertex to "selected"; increases count of sjelected vertices
  def selectVertex(vertex: Vertex): Boolean = {

    if(!vertex.selected) {
      select_Vs += 1;
      vertex.selected = true;
    }

    vertex.selected;
  }

  //changes state of the input vertex to "not selected"; decreases count of selected vertices
  def deselectVertex(vertex: Vertex): Boolean = {

    if(vertex.selected) {
      select_Vs -= 1;
      vertex.selected = false;
    }

    vertex.selected;
  }

  def changeSelection(vertex: Vertex): Boolean = {

    if(vertex.selected) {
      deselectVertex(vertex);
    } else {
      selectVertex(vertex);
    }
  }

  def getTouchedVertex(mouseX: Int, mouseY: Int, graph: List[Vertex]): Vertex = {

    graph.foreach {
      (vertex: Vertex) =>

        if(isPointInRect(mouseX, mouseY,
          vertex.X - VERTEX_WIDTH / 2, vertex.Y - VERTEX_HEIGHT / 2,
          vertex.X + VERTEX_WIDTH / 2, vertex.Y + VERTEX_HEIGHT / 2)) {

          return vertex;
        }
    }

    null;
  }



  //changes coordinates of all selected vertices according to difference between canvas.mouseX/Y and e.clientX/Y values
  def moveSelectedVertices(e: Event, graph: List[Vertex]) {

    //prepare
    val mouseX = e.clientX;
    val mouseY = e.clientY;
    val diffX = - mouse_x + mouseX;
    val diffY = - mouse_y + mouseY;
    var resultX = 0;
    var resultY = 0;

    //change position of all selected vertices
    graph.foreach {
      (vertex: Vertex) =>
        if(vertex.selected) {

          resultX = diffX + vertex.X;
          vertex.X = if(resultX < 0) { 0; } else { if(resultX > canvas_width) { canvas_width; } else { resultX; }};

          resultY = diffY + vertex.Y;
          vertex.Y = if(resultY < 0) { 0; } else { if(resultY > canvas_height) { canvas_height; } else { resultY; }};
        }
    }

    redraw(graph, ctx);
  }

  def selectionByRect(e: Event, graph: List[Vertex]) {

    //top values of mouse positoins on screen
    val mouseLeft = if(e.clientX <= select_rectOriginX) { e.clientX; } else { select_rectOriginX; };
    val mouseRight = if(e.clientX > select_rectOriginX) { e.clientX; } else { select_rectOriginX; };
    val mouseTop = if(e.clientY <= select_rectOriginY) { e.clientY; } else { select_rectOriginY; };
    val mouseBottom = if(e.clientY > select_rectOriginY) { e.clientY; } else { select_rectOriginY; };

    //VsLastTime takes care of not deselectin vertices, that were already selected at the beginning
    //of this action; only vertices in VsLastTime can be deselected
    graph.foreach {
      (vertex: Vertex) =>

        if(isPointInRect(vertex.X, vertex.Y,
          mouseLeft, mouseTop, mouseRight, mouseBottom)) {

          if(!vertex.selected) { //ingnore if vertex is already selected
            select_VsLastTime = addToArray(select_VsLastTime, vertex);
            selectVertex(vertex);
          }
        } else {

          val select_VSLastTime_temp = removeFromArray(select_VsLastTime, vertex);
          if(select_VSLastTime_temp != null) {
            select_VsLastTime = select_VSLastTime_temp;
            deselectVertex(vertex);
          }
        }
    }

    redraw(graph, ctx);
    Drawer.drawSelectionByRect(mouseLeft, mouseTop, mouseRight, mouseBottom, COLOR_SELECTION_RECT, ctx);
  }



  /////////////////////////////////////////////////////////////////////////////////////////////////
  //util funcions
  /////////////////////////////////////////////////////////////////////////////////////////////////
  def addToArray(array: List[Vertex], newElement: Vertex): List[Vertex] = {
    array.:::(List[Vertex](newElement));
  }

  def removeFromArray(array: List[Vertex], oldElement: Vertex): List[Vertex] = {

    var num = 0;
    var arrHead = List[Vertex]();
    
    array.foreach {
      (element: Vertex) =>

      if(element.id == oldElement.id) {

        return array.drop(num) ::: arrHead;
      }
      
      arrHead = arrHead ::: List[Vertex](element);
      num += 1;
    }

    null;
  }



  /////////////////////////////////////////////////////////////////////////////////////////////////
  //listener funcions
  /////////////////////////////////////////////////////////////////////////////////////////////////



  //reaction to mouse button pressed action
  def MOUSE_CLICKED(e: Event) {

    mouse_useToleration = true;
    mouse_buttonDownMove = false;
    mouse_buttonDown = true;
    mouse_x = e.clientX;
    mouse_y = e.clientY;
    select_touchedSelected = false;
    select_VsLastTime = List[Vertex]();


    var needsToRedraw = false;

    val touchedVertex = getTouchedVertex(mouse_x, mouse_y, graph);
    if(touchedVertex != null) {

      if(touchedVertex.selected) {
        select_touchedSelected = true;
      } else {
        if(!keyboard_shiftDown) {
          deselectAll(graph);
        }
        selectVertex(touchedVertex);
        needsToRedraw = true;
      }
    } else {

      if(!keyboard_shiftDown) {
        needsToRedraw = deselectAll(graph);
      }
      select_rect = true;
      select_rectOriginX = mouse_x;
      select_rectOriginY = mouse_y;
    }

    if(needsToRedraw) {
      redraw(graph, ctx);
    }
  }

  //reaction to mouse button released action
  def MOUSE_RELEASED(e: Event) {

    mouse_x = e.clientX;
    mouse_y = e.clientY;
    mouse_buttonDown = false;
    var needToRedraw = select_rect;

    if(select_touchedSelected && !mouse_buttonDownMove) {
      val touchedVertex = getTouchedVertex(mouse_x, mouse_y, graph);
      deselectVertex(touchedVertex);
      needToRedraw = true;
    } else if(mouse_useToleration) {
      needToRedraw = true;
    }

    if(needToRedraw) {
      redraw(graph, ctx);
    }
    select_rect = false;
    mouse_buttonDownMove = false;
  }

  //reaction to mouse move action
  def MOUSE_MOVED(e: Event) {

    if(mouse_buttonDown) {
      if(select_rect) {

        //if mouse is further than move toleration allows forbid tolerating
        //slight movement
        if(scala.math.abs(select_rectOriginX - e.clientX) > mouse_moveToleration ||
          scala.math.abs(select_rectOriginY - e.clientY) > mouse_moveToleration) {
          mouse_useToleration = false;
        }
        selectionByRect(e, graph);
      } else {
        moveSelectedVertices(e, graph);
      }

      mouse_buttonDownMove = true;
    }

    mouse_x = e.clientX;
    mouse_y = e.clientY;
  }

  def mouseWheel(e: Event) {

    /*var evt=window.event || e //equalize event object
   var delta=evt.detail? evt.detail*(-120) : evt.wheelDelta
   //delta returns +120 when wheel is scrolled up, -120 when scrolled down
   global.scale = (delta <= -120) ? global.scale - 0.1 : global.scale + 0.1

   global.scale = global.scale > 15 ? 15 : (global.scale < 8) ? 8 : global.scale
   //drawDefault();*/
  }

  /*var mousewheelevt=(/Firefox/i.test(navigator.userAgent))? "DOMMouseScroll" : "mousewheel"
//FF doesn't recognize mousewheel as of FF3.x

if (this.attachEvent) //if IE (and Opera depending on user setting)
  this.attachEvent("on"+mousewheelevt, mouseWheel)
else if (this.addEventListener) //WC3 browsers
  this.addEventListener(mousewheelevt, mouseWheel, false)*/


  //reaction to a pressed keyboard button
  def KEY_DOWN(e: Event) {

    if(e.keyCode == 16) {
      keyboard_shiftDown = true;
    }
  }

  //reaction to a released keyboard button
  def KEY_UP(e: Event) {

    if(e.keyCode == 16) {
      keyboard_shiftDown = false;
    }
  }
}