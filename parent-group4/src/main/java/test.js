var size = ['2.0', '3.0', '4.0']; //
var type = ['2g', '3g', '4g'];


// for test;
var sizeChecked = ['2.0', '3.0', '4.0'];
var typeChecked = ['2g', '3g'];


var tableData = []; // List<lineData>

// 行数 = size.checkedNum * type.checkedNum ||  size.checkedNum + type.checkedNum
var lineNum = 0;
var allChecked = (sizeChecked.length !== 0) && (typeChecked.length !== 0);

if (allChecked) {
    lineNum = sizeChecked.length * typeChecked.length;
} else {
    lineNum = sizeChecked.length + typeChecked.length;
}

for (var i = 1; i <= lineNum; i++) {
    if (sizeChecked.length === 0) {
        tableData.push({
            type: typeChecked[i - 1]
        });
    } else if (typeChecked.length === 0) {
        tableData.push({
            size: sizeChecked[i - 1]
        })
    } else {

        var sizeCheckedIndex = Math.ceil(i / typeChecked.length) - 1;
        var typeCheckedIndex = i - typeChecked.length * sizeCheckedIndex - 1;
        console.log(i, typeChecked.length, typeCheckedIndex, sizeCheckedIndex);
        tableData.push({
            type: typeChecked[typeCheckedIndex],
            size: sizeChecked[sizeCheckedIndex],
        });
    }
}

console.log(tableData)