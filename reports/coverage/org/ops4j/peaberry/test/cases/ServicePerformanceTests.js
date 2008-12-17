var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":1204,"sl":36,"methods":[{"sl":39,"el":60,"sc":3},{"sl":92,"el":106,"sc":3},{"sl":108,"el":110,"sc":3},{"sl":112,"el":118,"sc":3}],"el":119,"name":"ServicePerformanceTests"},{"id":1211,"sl":62,"methods":[],"el":64,"name":"ServicePerformanceTests.Example"},{"id":1211,"sl":66,"methods":[{"sl":68,"el":70,"sc":5}],"el":71,"name":"ServicePerformanceTests.ExampleImpl"},{"id":1213,"sl":73,"methods":[],"el":90,"name":"ServicePerformanceTests.Holder"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_10":{"methods":[{"sl":68},{"sl":92},{"sl":108},{"sl":112}],"name":"testServiceLookupPerformance","statements":[{"sl":69},{"sl":94},{"sl":95},{"sl":98},{"sl":100},{"sl":101},{"sl":103},{"sl":104},{"sl":105},{"sl":109},{"sl":113},{"sl":114},{"sl":115},{"sl":117}],"pass":true},"test_20":{"methods":[{"sl":39}],"name":"configure","statements":[{"sl":43},{"sl":46},{"sl":49},{"sl":52},{"sl":56},{"sl":59}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [20], [], [], [], [20], [], [], [20], [], [], [20], [], [], [20], [], [], [], [20], [], [], [20], [], [], [], [], [], [], [], [], [10], [10], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [10], [], [10], [10], [], [], [10], [], [10], [10], [], [10], [10], [10], [], [], [10], [10], [], [], [10], [10], [10], [10], [], [10], [], []]
