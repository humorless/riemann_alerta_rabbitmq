package main

import (
	"github.com/amir/raidman"
	"time"
)

func main() {
	c, err := raidman.Dial("tcp", "10.20.30.40:5555")
	if err != nil {
		panic(err)
	}

	// send multiple events at once
	for i := 0; i < 10; i++ {
		err = c.SendMulti([]*raidman.Event{
			&raidman.Event{
				State:      "success",
				Host:       "ctl-00",
				Service:    "net.if.in.bits/iface=eth_all",
				Metric:     10 * i,
				Ttl:        10,
				Attributes: map[string]string{"platform": "c01.i01"},
			},
			&raidman.Event{
				State:      "failure",
				Host:       "ctl-01",
				Service:    "net.if.in.bits/iface=eth_all",
				Metric:     10 * (i + 1),
				Ttl:        10,
				Attributes: map[string]string{"platform": "c01.i01"},
			},
			&raidman.Event{
				State:      "success",
				Host:       "ctl-02",
				Service:    "net.if.in.bits/iface=eth_all",
				Metric:     10 * (i + 2),
				Ttl:        10,
				Attributes: map[string]string{"platform": "c01.p02"},
			},
		})
		if err != nil {
			panic(err)
		}
		time.Sleep(5000 * time.Millisecond)
	}

	events, err := c.Query("host = \"ctl-00\"")
	if err != nil {
		panic(err)
	}

	if len(events) < 1 {
		panic("Submitted event not found")
	}

	c.Close()
}
