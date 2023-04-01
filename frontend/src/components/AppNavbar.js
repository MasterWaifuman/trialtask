import React from 'react';
import {Navbar, NavbarBrand, NavLink, Nav, NavItem} from 'reactstrap';
import {Link} from 'react-router-dom';

const AppNavbar = () => {
    return <Navbar color='dark' dark expand='lg'>
            <NavbarBrand tag={Link} to="/">Home</NavbarBrand>
            <Nav className='ml-auto' navbar>
                <NavItem>
                    <NavLink tag={Link} to='/api/order'>Order</NavLink>
                </NavItem>
                <NavItem>
                    <NavLink tag={Link} to='/api/weather'>Weather</NavLink>
                </NavItem>
            </Nav>
    </Navbar>;
}

export default AppNavbar