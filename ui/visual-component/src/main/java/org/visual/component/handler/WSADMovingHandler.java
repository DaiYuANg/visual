package org.visual.component.handler;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 *
 * <pre>
 * pane.setOnKeyPressed(wsadHandler);
 * pane.setOnKeyReleased(wsadHandler);
 * </pre>
 *
 * *
 */
public abstract class WSADMovingHandler implements EventHandler<KeyEvent> {
  private final double xSpeed;
  private final double ySpeed;
  private long time = 0;
  private boolean w = false;
  private boolean s = false;
  private boolean wsIsW = false;
  private boolean a = false;
  private boolean d = false;
  private boolean adIsA = false;
  private AnimationTimer timer = null;
  private double x = 0.0;
  private double y = 0.0;

  public WSADMovingHandler(double xSpeed, double ySpeed) {
    this.xSpeed = xSpeed;
    this.ySpeed = ySpeed;
  }

  @Override
  public void handle(KeyEvent event) {
    if (event.getEventType() == KeyEvent.KEY_PRESSED) {
      onPressed(event.getCode());
    } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
      onReleased(event.getCode());
    }
  }

  private void onPressed(KeyCode code) {
    switch (code) {
      case W:
        if (!w) {
          w = true;
          wsIsW = true;
          record();
        }
        break;
      case S:
        if (!s) {
          s = true;
          wsIsW = false;
          record();
        }
        break;
      case A:
        if (!a) {
          a = true;
          adIsA = true;
          record();
        }
        break;
      case D:
        if (!d) {
          d = true;
          adIsA = false;
          record();
        }
        break;
      default:
        return;
    }
    if (w || s || a || d) {
      if (timer == null) {
        timer = new Timer();
        timer.start();
      }
    }
  }

  private void record() {
    time = System.nanoTime();
    double[] xy = get();
    x = xy[0];
    y = xy[1];
  }

  private void onReleased(KeyCode code) {
    switch (code) {
      case W:
        if (w) {
          w = false;
          wsIsW = false;
          record();
        }
        break;
      case S:
        if (s) {
          s = false;
          wsIsW = true;
          record();
        }
        break;
      case A:
        if (a) {
          a = false;
          adIsA = false;
          record();
        }
        break;
      case D:
        if (d) {
          d = false;
          adIsA = true;
          record();
        }
        break;
      default:
        return;
    }
    if (!w && !s && !a && !d) {
      AnimationTimer timer = this.timer;
      this.timer = null;
      if (timer != null) {
        timer.stop();
      }
    }
    if (!w && !s && !a && !d) {
      time = 0;
    }
  }

  private class Timer extends AnimationTimer {
    @Override
    public void handle(long now) {
      if (time == 0) {
        return;
      }
      long delta = now - time;
      if (delta < 0) return;
      double x = WSADMovingHandler.this.x;
      double y = WSADMovingHandler.this.y;
      if (w || s) {
        if (wsIsW) {
          y -= ySpeed * delta / 1000000;
        } else {
          y += ySpeed * delta / 1000000;
        }
      }
      if (a || d) {
        if (adIsA) {
          x -= xSpeed * delta / 1000000;
        } else {
          x += xSpeed * delta / 1000000;
        }
      }
      set(x, y);
    }
  }

  protected abstract void set(double x, double y);

  protected abstract double[] get();
}
