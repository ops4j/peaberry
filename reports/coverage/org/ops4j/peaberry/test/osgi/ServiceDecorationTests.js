var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":604,"sl":40,"methods":[{"sl":43,"el":54,"sc":3},{"sl":112,"el":123,"sc":3},{"sl":125,"el":135,"sc":3}],"el":136,"name":"ServiceDecorationTests"},{"id":608,"sl":56,"methods":[{"sl":58,"el":69,"sc":5},{"sl":61,"el":63,"sc":9},{"sl":65,"el":67,"sc":9}],"el":70,"name":"ServiceDecorationTests.BrokenDecorator"},{"id":614,"sl":72,"methods":[{"sl":75,"el":96,"sc":5},{"sl":80,"el":92,"sc":9},{"sl":94,"el":94,"sc":9}],"el":97,"name":"ServiceDecorationTests.StickyDecorator"},{"id":631,"sl":99,"methods":[],"el":103,"name":"ServiceDecorationTests.Holder"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_12":{"methods":[{"sl":80},{"sl":94},{"sl":125}],"name":"stickyService","statements":[{"sl":81},{"sl":82},{"sl":83},{"sl":84},{"sl":87},{"sl":91},{"sl":126},{"sl":127},{"sl":128},{"sl":129},{"sl":130},{"sl":131},{"sl":132},{"sl":133},{"sl":134}],"pass":true},"test_0":{"methods":[{"sl":58},{"sl":61},{"sl":112}],"name":"brokenServices","statements":[{"sl":60},{"sl":62},{"sl":113},{"sl":115},{"sl":116},{"sl":117},{"sl":119},{"sl":120},{"sl":122}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [0], [], [0], [0], [0], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [12], [12], [12], [12], [12], [], [], [12], [], [], [], [12], [], [], [12], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [0], [0], [], [0], [0], [0], [], [0], [0], [], [0], [], [], [12], [12], [12], [12], [12], [12], [12], [12], [12], [12], [], []]
