function pageName(name) {
    var $session = $jsapi.context().session;
    $session.pageName = name;
}

function rememberLastState() {
    var $session = $jsapi.context().session;
    $session.lastState = currentState();
    log("REMEMBER LAST STATE: " + $session.lastState)
}

function currentState() {
  return $jsapi.context().currentState;
}

function testMode() {
    return !!$jsapi.context().testContext;
}