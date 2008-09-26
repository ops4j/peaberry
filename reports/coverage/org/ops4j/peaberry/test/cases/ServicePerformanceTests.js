var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":1278,"sl":37,"methods":[{"sl":40,"el":61,"sc":3},{"sl":93,"el":109,"sc":3},{"sl":111,"el":117,"sc":3}],"el":118,"name":"ServicePerformanceTests"},{"id":1285,"sl":63,"methods":[],"el":65,"name":"ServicePerformanceTests.Example"},{"id":1285,"sl":67,"methods":[{"sl":69,"el":71,"sc":5}],"el":72,"name":"ServicePerformanceTests.ExampleImpl"},{"id":1287,"sl":74,"methods":[],"el":91,"name":"ServicePerformanceTests.Holder"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_4":{"methods":[{"sl":40}],"name":"configure","statements":[{"sl":44},{"sl":47},{"sl":50},{"sl":53},{"sl":57},{"sl":60}],"pass":true},"test_19":{"methods":[{"sl":69},{"sl":93},{"sl":111}],"name":"testServiceLookupPerformance","statements":[{"sl":70},{"sl":95},{"sl":96},{"sl":99},{"sl":100},{"sl":101},{"sl":102},{"sl":104},{"sl":105},{"sl":106},{"sl":107},{"sl":108},{"sl":112},{"sl":113},{"sl":114},{"sl":116}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [4], [], [], [], [4], [], [], [4], [], [], [4], [], [], [4], [], [], [], [4], [], [], [4], [], [], [], [], [], [], [], [], [19], [19], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [19], [], [19], [19], [], [], [19], [19], [19], [19], [], [19], [19], [19], [19], [19], [], [], [19], [19], [19], [19], [], [19], [], []]
