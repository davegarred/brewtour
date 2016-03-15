var Layout = React.createClass({
	render: function() {
		return (
			<div>
				<Header></Header>
			</div>
		)
	}
});

ReactDOM.render(
	<Layout />,
	document.getElementById('content')
);