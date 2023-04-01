import React, { useState } from 'react';
import { useNavigate} from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar.js';

function Order() {
    const [order, setOrder] = useState({
        order: 'something',
        city: '',
        vehicle: '',
        fee: ''
    });
    const navigate = useNavigate();
    const [error, setError] = useState('');

    const handleChange = (e) => {
        const value = e.target.value;
        setOrder({...order, [e.target.name]: value});
    };

    const saveOrder = async(e) => {
        e.preventDefault();
        setError('');
        try {
        await fetch('/api/order', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(order)
        }).then(response => response.json()).then(data => setOrder(data));
        } catch(err) {
            setError('Inserted order data is invalid');
            console.log(error);
        }
    };

    return <div>
            <AppNavbar/>
            <Container>
                <Form>
                    <FormGroup>
                        <Label for="order">Your order</Label>
                        <Input disabled type="text" name="order" id="order" value={order.order}
                                onChange={(e) => handleChange(e)} autoComplete="order"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="city">City</Label>
                        <Input type="text" name="city" id="city" value={order.city}
                               onChange={(e) => handleChange(e)} autoComplete="city"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="vehicle">Vehicle</Label>
                        <Input type="text" name="vehicle" id="vehicle" value={order.vehicle}
                               onChange={(e) => handleChange(e)} autoComplete="vehicle"/>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit" onClick={saveOrder}>Order</Button>{' '}
                        <Button color="secondary" onClick={() => navigate(-1)}>Cancel</Button>
                    </FormGroup>
                </Form>
                {order.fee !== '' && error === '' && (
                    <div className="alert alert-success">Delivery fee: {order.fee}€</div>
                )
                }
                {error !== '' && (
                        <div className="alert alert-danger">{error}</div>
                    )
                }
            </Container>
        </div>
}
export default Order;