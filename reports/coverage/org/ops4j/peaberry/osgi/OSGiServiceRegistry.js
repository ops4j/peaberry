var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":856,"sl":36,"methods":[{"sl":45,"el":48,"sc":3},{"sl":50,"el":52,"sc":3},{"sl":54,"el":56,"sc":3},{"sl":58,"el":63,"sc":3},{"sl":65,"el":70,"sc":3},{"sl":72,"el":75,"sc":3},{"sl":77,"el":80,"sc":3},{"sl":82,"el":88,"sc":3},{"sl":90,"el":105,"sc":3}],"el":106,"name":"OSGiServiceRegistry"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_28":{"methods":[{"sl":54}],"name":"testServiceInjection","statements":[{"sl":55}],"pass":true},"test_11":{"methods":[{"sl":50},{"sl":54},{"sl":90}],"name":"testServiceLookupPerformance","statements":[{"sl":51},{"sl":55},{"sl":91},{"sl":92},{"sl":94},{"sl":95},{"sl":96},{"sl":97},{"sl":98},{"sl":99},{"sl":100},{"sl":104}],"pass":true},"test_9":{"methods":[{"sl":50},{"sl":54},{"sl":90}],"name":"testDecoratedServiceInjection","statements":[{"sl":51},{"sl":55},{"sl":91},{"sl":92},{"sl":94},{"sl":95},{"sl":104}],"pass":true},"test_5":{"methods":[{"sl":50},{"sl":54},{"sl":90}],"name":"testDirectServiceInjection","statements":[{"sl":51},{"sl":55},{"sl":91},{"sl":92},{"sl":94},{"sl":95},{"sl":104}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [5, 9, 11], [5, 9, 11], [], [], [5, 28, 9, 11], [5, 28, 9, 11], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [5, 9, 11], [5, 9, 11], [5, 9, 11], [], [5, 9, 11], [5, 9, 11], [11], [11], [11], [11], [11], [], [], [], [5, 9, 11], [], []]
