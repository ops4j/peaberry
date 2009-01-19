var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":1587,"sl":36,"methods":[{"sl":50,"el":71,"sc":3},{"sl":92,"el":110,"sc":3},{"sl":112,"el":114,"sc":3},{"sl":116,"el":123,"sc":3}],"el":124,"name":"ServicePerformanceTests"},{"id":1587,"sl":39,"methods":[],"el":41,"name":"ServicePerformanceTests.Example"},{"id":1587,"sl":43,"methods":[{"sl":45,"el":47,"sc":5}],"el":48,"name":"ServicePerformanceTests.ExampleImpl"},{"id":1596,"sl":73,"methods":[],"el":90,"name":"ServicePerformanceTests.Holder"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_11":{"methods":[{"sl":45},{"sl":92},{"sl":112},{"sl":116}],"name":"testServiceLookupPerformance","statements":[{"sl":46},{"sl":94},{"sl":95},{"sl":98},{"sl":99},{"sl":100},{"sl":102},{"sl":103},{"sl":105},{"sl":106},{"sl":107},{"sl":109},{"sl":113},{"sl":117},{"sl":118},{"sl":119},{"sl":120},{"sl":122}],"pass":true},"test_21":{"methods":[{"sl":50}],"name":"configure","statements":[{"sl":54},{"sl":57},{"sl":60},{"sl":63},{"sl":67},{"sl":70}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [11], [11], [], [], [], [21], [], [], [], [21], [], [], [21], [], [], [21], [], [], [21], [], [], [], [21], [], [], [21], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [11], [], [11], [11], [], [], [11], [11], [11], [], [11], [11], [], [11], [11], [11], [], [11], [], [], [11], [11], [], [], [11], [11], [11], [11], [11], [], [11], [], []]
