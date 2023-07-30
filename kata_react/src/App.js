import logo from "./logo.svg";
import "./App.css";
import { useState, Fragment } from "react";

let nextId = 0;
const regexForNumber = new RegExp("^[0-9]*(.)?[0-9]*$");

function App() {
	const [value, setValue] = useState("");
	const [stack, setStack] = useState([]);

	const changeValue = (e) => setValue(e.target.value);

	const addValueToStack = () => {
		if (!regexForNumber.test(value) || value === "") {
			return;
		}

		let finalValue = value;

		if (value[0] === ".") {
			finalValue = "0" + value;
		}

		if (value.slice(-1) === ".") {
			finalValue = finalValue.slice(0, -1);
		}

		setStack([...stack, { id: nextId++, name: finalValue }]);
	};

	const resetStack = () => {
		setStack([]);
	};

	const plusOp = () => {
		if (stack.length < 2) {
			return;
		}

		const firstElmt = stack[stack.length - 1];
		const secndElmt = stack[stack.length - 2];

		const newValue = Number(firstElmt.name) + Number(secndElmt.name);

		let newStack = stack.slice(0, -2);
		nextId = nextId - 2;

		setStack([...newStack, { id: nextId++, name: newValue }]);
	};

	const minusOp = () => {
		if (stack.length < 2) {
			return;
		}

		const firstElmt = stack[stack.length - 1];
		const secndElmt = stack[stack.length - 2];

		const newValue = Number(firstElmt.name) - Number(secndElmt.name);

		let newStack = stack.slice(0, -2);
		nextId = nextId - 2;

		setStack([...newStack, { id: nextId++, name: newValue }]);
	};

	const timesOp = () => {
		if (stack.length < 2) {
			return;
		}

		const firstElmt = stack[stack.length - 1];
		const secndElmt = stack[stack.length - 2];

		const newValue = Number(firstElmt.name) * Number(secndElmt.name);

		let newStack = stack.slice(0, -2);
		nextId = nextId - 1;

		setStack([...newStack, { id: nextId++, name: newValue }]);
	};

	const dividedByOp = () => {
		if (stack.length < 2) {
			return;
		}

		const firstElmt = stack[stack.length - 1];
		const secndElmt = stack[stack.length - 2];

		if (secndElmt.name === "0") {
			return;
		}

		const newValue = Number(firstElmt.name) / Number(secndElmt.name);

		let newStack = stack.slice(0, -2);
		nextId = nextId - 2;

		setStack([...newStack, { id: nextId++, name: newValue }]);
	};

	return (
		<Fragment>
			<h1>RPN calculator</h1>
			<label>Please enter a number: </label>
			<input value={value} onChange={changeValue} />
			<button onClick={addValueToStack}>Add value</button>
			<button onClick={resetStack}>Reset stack</button>
			<br />
			<button onClick={plusOp}>+</button>
			<button onClick={minusOp}>-</button>
			<button onClick={timesOp}>*</button>
			<button onClick={dividedByOp}>/</button>
			<h3>RPN calculator</h3>
			<ul>
				{stack.map((value) => (
					<li key={value.id}>{value.name}</li>
				))}
			</ul>
		</Fragment>
	);
}

export default App;
