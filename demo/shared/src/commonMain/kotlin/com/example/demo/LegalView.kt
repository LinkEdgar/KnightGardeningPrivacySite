package com.example.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog



private  val privacyPolicy = """
                    DATA_COLLECTION_NOTICE:
                   
                    TLDR: We do not collect any information. Privcay still exists.
                    
                    1. IDENTIFIERS: Usernames used for leaderboard and games mode such as PVP are not unique and we do not use any kind of tracking information such as device ID.
                    
                    2. NEURAL_TELEMETRY: We do not collect information even in an anonymous manner.
                    
                    3. LOCAL_STORAGE: There is a local database that's created for offline use and stores leaderboard and audio information.
                    
                """.trimIndent()

private val eulaAgreement = """
                    TERMS_OF_SYNCHRONIZATION:
                    By entering the Matrix, you agree to the following protocols:
                    
                    1. USE_OF_INTERFACE: You are granted a non-exclusive, non-transferable license to refine data nodes for personal synchronization.
                    
                    2. PROHIBITED_ACTIONS: Cheating is highly discouraged. Please just lock in.
                    
                    3. LIMITATION_OF_LIABILITY: The Architects are not responsible for any neural feedback, time-vortex displacement, or data corruption resulting from unauthorized refinement sessions.
                    
                    4. TERMINATION: Your access to the interface may be terminated if you fail to comply with the standard Gardener conduct protocols.
                """.trimIndent()

@Preview
@Composable
fun KnightGardeningPreview() {
    KnightGardeningStaticSite()
}

@Composable
fun KnightGardeningStaticSite() {
    var showPrivacyDialog by remember { mutableStateOf(false) }
    var showEulaDialog by remember { mutableStateOf(false) }
    Box(
        Modifier
            .fillMaxSize()
            .background(MATRIX_BACKGROUND_COLOR)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    alpha = .4f
                }
        ) {
            MatrixRain()
        }

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GlitchTitle("Knight Gardening")

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "WILL_YOU_ESCAPE?",
                color = MATRIX_GREEN.copy(alpha = 0.87f),
                fontFamily = FontFamily.Monospace,
                fontSize = 12.sp,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "PRIVACY_POLICY",
                color = MATRIX_GREEN.copy(alpha = 0.6f),
                fontFamily = FontFamily.Monospace,
                fontSize = 10.sp,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    showPrivacyDialog = true
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "END_USER_LICENESE_AGREEMENT",
                color = MATRIX_GREEN.copy(alpha = 0.6f),
                fontFamily = FontFamily.Monospace,
                fontSize = 10.sp,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    showEulaDialog = true
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Contact Us: KnightGardeningGame@gmail.com",
                color = MATRIX_GREEN.copy(alpha = 0.6f),
                fontFamily = FontFamily.Monospace,
                fontSize = 10.sp,
            )
        }

        if (showEulaDialog) {
            Dialog(onDismissRequest = { showEulaDialog = false }) {
                LegalView(
                    onAcknowledge = {showEulaDialog = false},
                    content = eulaAgreement,
                    "END_USER_LICENSE_AGREEMENT (EULA)"
                )
            }
        }

        if (showPrivacyDialog) {
            Dialog(onDismissRequest = { showPrivacyDialog = false }) {
                LegalView(
                    onAcknowledge = { showPrivacyDialog = false },
                    content = privacyPolicy,
                    title = "PRIVACY_POLICY"
                )
            }
        }
    }
}

@Composable
fun LegalView(
    onAcknowledge: () -> Unit,
    content: String,
    title: String
) {
    LegalContent(
        onAcknowledge = { onAcknowledge() },
        content = content,
        title = title
    )
}

@Preview
@Composable
fun LegalViewPreview() {
    LegalContent(
        onAcknowledge = {},
        content = privacyPolicy,
        title = "01. PRIVACY_POLICY"
    )
}

@Composable
fun LegalContent(
    onAcknowledge: () -> Unit,
    content: String,
    title: String
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MATRIX_BACKGROUND_COLOR)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MATRIX_BACKGROUND_COLOR)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GlitchTitle(text = "LEGAL_PROTOCOLS")

                Spacer(modifier = Modifier.height(32.dp))

                LegalSection(
                    title = title,
                    content = content
                )

                Spacer(modifier = Modifier.height(48.dp))

                Text(
                    text = "LAST_UPDATE: SEC_CYCLE_2026.06",
                    color = MATRIX_GREEN.copy(alpha = 0.3f),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 10.sp
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                TerminalButton(
                    text = "ACKNOWLEDGE",
                    onClick = onAcknowledge
                )
            }
        }
        ScanlineOverlay()
    }
}


@Composable
private fun LegalSection(
    title: String,
    content: String
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            color = MATRIX_GREEN,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = content,
            color = Color.White.copy(alpha = 0.7f),
            fontFamily = FontFamily.Monospace,
            fontSize = 12.sp,
            lineHeight = 18.sp
        )
    }
}