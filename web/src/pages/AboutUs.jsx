import { StickyScroll } from '@/components/ui/sticky-scroll-reveal'
import aboutUsContent from '@/lib/aboutUsContent'
import React from 'react'

const AboutUs = () => {
  return (
    <>
      <StickyScroll content={aboutUsContent}/>
    </>
  )
}

export default AboutUs
